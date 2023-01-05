package ru.karod.tsm.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import ru.karod.tsm.security.request.LoginRequest;
import ru.karod.tsm.security.request.SignupRequest;
import ru.karod.tsm.services.AuthenticationService;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.util.MessageResponse;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthenticationService tsmAuthenticationServiceImpl;
    private final UserService tsmUserServiceImpl;

    @PostMapping("/sign_up")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid
                                               SignupRequest signupRequest,
                                               BindingResult bindingResult,
                                               HttpServletRequest request) {
        tsmAuthenticationServiceImpl.register(signupRequest, bindingResult, request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid
                                                   LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
        return tsmAuthenticationServiceImpl.login(loginRequest, bindingResult);
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestParam("code") String code) {

        tsmUserServiceImpl.verify(code);
        return ResponseEntity.ok(new MessageResponse("User verified successfully!"));
    }

}
