package ru.karod.tsm.services.impl;

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
import ru.karod.tsm.services.AuthenticationService;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.validations.UserValidator;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import static ru.karod.tsm.util.ErrorUtil.createErrorMessageToClient;

@Service
@RequiredArgsConstructor
public class TsmAuthenticationServiceImpl implements AuthenticationService
{
    private final ModelMapper modelMapper;
    private final UserService tsmUserServiceImpl;
    private final AuthenticationManager authenticationManager;
    private final JWTTokenProvider jwtTokenProvider;
    private final UserValidator userValidator;

    @Override
    public ResponseEntity<Object> login(@NotNull final LoginRequest loginRequest, @NotNull final BindingResult bindingResult)
    {
        if (bindingResult.hasErrors())
        {
            String errorMsg = createErrorMessageToClient(bindingResult);
            throw new AuthException(errorMsg);
        }

        String userEmail = loginRequest.getEmail();
        User user = tsmUserServiceImpl.findUserByEmail(userEmail).orElseThrow(
                () -> new NotFoundException("User with email " + userEmail + " cannot be found")
        );
        if (!user.isVerified())
        {
            throw new AuthException("User email isn't verified");
        }

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword());
        try
        {
            Authentication authentication = authenticationManager.authenticate(authToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = SecurityConstants.TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
            return ResponseEntity.ok(new JWTTokenSuccessResponse(jwt));
        }
        catch (BadCredentialsException exc)
        {
            throw new AuthException("Incorrect credentials");
        }
    }

    @Override
    public void register(@NotNull final SignupRequest signupRequest, @NotNull final BindingResult bindingResult, HttpServletRequest request)
    {
        User user = modelMapper.map(signupRequest, User.class);
        userValidator.validate(user, bindingResult);
        if (bindingResult.hasErrors())
        {
            String errorMsg = createErrorMessageToClient(bindingResult);
            throw new AuthException(errorMsg);
        }
        tsmUserServiceImpl.register(user, getSiteURL(request));
    }

    private String getSiteURL(HttpServletRequest request)
    {
        String siteURL = request.getRequestURL().toString();
        return siteURL.replace(request.getServletPath(), "");
    }
}
