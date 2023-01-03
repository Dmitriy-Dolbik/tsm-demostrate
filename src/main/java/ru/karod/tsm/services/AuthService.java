package ru.karod.tsm.services;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import ru.karod.tsm.exceptions.AuthException;
import ru.karod.tsm.exceptions.NotFoundException;
import ru.karod.tsm.models.User;
import ru.karod.tsm.security.JWTTokenProvider;
import ru.karod.tsm.security.SecurityConstants;
import ru.karod.tsm.security.request.LoginRequest;
import ru.karod.tsm.security.request.SignupRequest;
import ru.karod.tsm.security.response.JWTTokenSuccessResponse;
import ru.karod.tsm.validations.UserValidator;

import javax.servlet.http.HttpServletRequest;

import static ru.karod.tsm.util.ErrorUtil.createErrorMessageToClient;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserValidator userValidator;

    public ResponseEntity<Object> login(LoginRequest loginRequest, BindingResult bindingResult){
        if (bindingResult.hasErrors()){
            String errorMsg = createErrorMessageToClient(bindingResult);
            throw new AuthException(errorMsg);
        }

        String userEmail = loginRequest.getEmail();
        User user = userService.findUserByEmail(userEmail).orElseThrow(
                () -> new NotFoundException("User with email " + userEmail + " cannot be found")
        );
        if (!user.isVerified()){
            throw new AuthException("User email isn't verified");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());
        try {
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt));
        } catch (BadCredentialsException exc) {
            throw new AuthException("Incorrect credentials");
        }
    }
    public void register (SignupRequest signupRequest, BindingResult bindingResult, HttpServletRequest request) {
        User user = modelMapper.map(signupRequest, User.class);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors()) {
            String errorMsg = createErrorMessageToClient(bindingResult);
            throw new AuthException(errorMsg);
        }
        userService.register(user, getSiteURL(request));
    }
    private String getSiteURL(HttpServletRequest request) {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }



}
