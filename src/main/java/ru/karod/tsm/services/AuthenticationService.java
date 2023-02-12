package ru.karod.tsm.services;

import javax.servlet.http.HttpServletRequest;
import javax.validation.constraints.NotNull;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import ru.karod.tsm.security.request.LoginRequest;
import ru.karod.tsm.security.request.SignupRequest;

/**
 *
 */
public interface AuthenticationService {
    ResponseEntity<Object> login(@NotNull final LoginRequest loginRequest, @NotNull final BindingResult bindingResult);
    void register (@NotNull final SignupRequest signupRequest, @NotNull final BindingResult bindingResult);
}
