package ru.karod.tsm.validations;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.karod.tsm.models.User;
import ru.karod.tsm.services.UserService;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserService userService;

    @Override
    public boolean supports(Class<?> clazz) {
        return User.class.equals(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        User user = (User) target;
        if (userService.findUserByEmail(user.getEmail()).isPresent()){
            errors.rejectValue("email", "", "User with this email already exists");
        }
    }

}
