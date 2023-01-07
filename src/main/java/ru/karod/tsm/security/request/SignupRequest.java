package ru.karod.tsm.security.request;

import lombok.Data;
import ru.karod.tsm.annotations.PasswordMatches;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.annotations.ValidPassword;

import javax.persistence.Column;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.util.List;

@Data
@PasswordMatches(message = "The entered passwords do not match")
public class SignupRequest {

    @NotEmpty(message = "Email should be not empty")
    @ValidEmail(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Role should be not empty")
    private String role;

    @Size(min = 2, max = 30, message = "First name should be between 2 and 30 characters")
    private String firstName;

    @Size(min = 2, max = 30, message = "Last name should be between 2 and 30 characters")
    private String lastName;

    @NotEmpty(message = "Languages should be not empty")
    private List<String> subjectIdList;

    @ValidPassword(message = "Please enter the password according to the requirements: " +
            "from 6 to 20 characters, at least one digit or Latin letter is required")
    private String password;
    private String confirmPassword;
}

