package ru.karod.tsm.controllers;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.karod.tsm.dto.SubjectDTO;
import ru.karod.tsm.dto.SubjectsResponse;
import ru.karod.tsm.security.request.LoginRequest;
import ru.karod.tsm.security.request.SignupRequest;
import ru.karod.tsm.services.AuthService;
import ru.karod.tsm.services.SubjectService;
import ru.karod.tsm.util.MessageResponse;

import javax.validation.Valid;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@CrossOrigin
@PreAuthorize("permitAll()")
public class AuthController {
    private final AuthService authService;
    private final SubjectService subjectService;
    private final ModelMapper modelMapper;
    @GetMapping("/subject")
    public SubjectsResponse getSubjects() {
        // оборачиваем список в объект для пересылки
        return new SubjectsResponse(subjectService.findAll().stream()
                .map(subject -> modelMapper.map(subject, SubjectDTO.class))
                .collect(Collectors.toList()));
    }

    @PostMapping("/sign_in")
    public ResponseEntity<Object> authenticateUser(@RequestBody @Valid
                                                       LoginRequest loginRequest,
                                                   BindingResult bindingResult){
        return authService.login(loginRequest, bindingResult);
    }
    @PostMapping("/sign_up")
    public ResponseEntity<Object> registerUser(@RequestBody @Valid
                                                   SignupRequest signupRequest,
                                               BindingResult bindingResult){
        authService.register(signupRequest, bindingResult);
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }

}
