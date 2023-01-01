package ru.karod.tsm.security.request;

import lombok.Data;
import ru.karod.tsm.annotations.PasswordMatches;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.annotations.ValidPassword;

import javax.validation.constraints.NotEmpty;
import java.util.List;

@Data
@PasswordMatches(message = "The entered passwords do not match")
public class SignupRequest {

    @NotEmpty(message = "Email should be not empty")
    @ValidEmail(message = "Invalid Email")
    private String email;

    @NotEmpty(message = "Role should be not empty")
    private String role;

    @NotEmpty(message = "Languages should be not empty")
    private List<String> subjectIdList;

    @ValidPassword(message = "Please enter the password according to the requirements: " +
            "from 6 to 20 characters, at least one digit or Latin letter is required")
    private String password;
    private String confirmPassword;
}

