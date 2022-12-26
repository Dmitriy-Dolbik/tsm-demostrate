package ru.karod.tsm.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.karod.tsm.validations.EmailValidator;
import ru.karod.tsm.validations.PasswordMatchesValidator;

import java.lang.annotation.*;

@Target({ElementType.TYPE, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordMatchesValidator.class)
@Documented
public @interface PasswordMatches {
    String message();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
