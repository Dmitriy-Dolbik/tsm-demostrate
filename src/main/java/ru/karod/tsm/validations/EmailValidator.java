package ru.karod.tsm.validations;

import ru.karod.tsm.annotations.ValidEmail;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    private static final String EMAIL_PATTERN = "^[a-zA-Z0-9._%+-]+@[a-zA-Z]+\\.[a-zA-Z]{2,6}$";

    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return email.matches(EMAIL_PATTERN);
    }
}
