package ru.karod.tsm.annotations;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import ru.karod.tsm.validations.EmailValidator;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailValidator.class)
@Documented
public @interface ValidEmail {
    String message();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
