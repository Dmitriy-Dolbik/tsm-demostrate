package ru.karod.tsm.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karod.tsm.security.request.LoginRequest;
import ru.karod.tsm.security.request.SignupRequest;
import ru.karod.tsm.services.AuthService;
import ru.karod.tsm.services.UserService;
import ru.karod.tsm.util.MessageResponse;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {
    private final AuthService authService;
    private final UserService userService;

    @PostMapping("/sign_up")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid
                                               SignupRequest signupRequest,
                                               BindingResult bindingResult,
                                               HttpServletRequest request) {
        authService.register(signupRequest, bindingResult, request);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid
                                                   LoginRequest loginRequest,
                                                   BindingResult bindingResult) {
        return authService.login(loginRequest, bindingResult);
    }

    @GetMapping("/verify")
    public ResponseEntity<Object> verifyUser(@RequestParam("code") String code) {

        userService.verify(code);
        return ResponseEntity.ok(new MessageResponse("User verified successfully!"));
    }

}
