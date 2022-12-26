package ru.karod.tsm.security.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import ru.karod.tsm.annotations.PasswordMatches;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.annotations.ValidPassword;

import java.util.List;

@Data
@PasswordMatches(message = "Password do not match")
public class SignupRequest {

    @NotEmpty(message = "Email should be not empty")
    @ValidEmail(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Role should be not empty")
    private String role;

    @NotEmpty(message = "Role should be not empty")
    private List<String> languages;

    @Column(name = "password")
    @ValidPassword(message = "Please enter the password according to the requirements: " +
            "from 6 to 20 characters, at least one digit or Latin letter is required")
    private String password;
    private String confirmPassword;
}
