package ru.karod.tsm.security.request;

import lombok.Data;
import ru.karod.tsm.annotations.ValidEmail;
import ru.karod.tsm.annotations.ValidPassword;

import javax.validation.constraints.NotEmpty;

@Data
public class LoginRequest {

    @NotEmpty(message = "Email should be not empty")
    @ValidEmail(message = "Invalid Email")
    private String email;

    @ValidPassword(message = "Please enter the password according to the requirements: " +
            "from 6 to 20 characters, at least one digit or Latin letter is required")
    private String password;
}
