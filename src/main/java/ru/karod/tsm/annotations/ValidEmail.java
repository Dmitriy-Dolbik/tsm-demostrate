package ru.karod.tsm.annotations;

import ru.karod.tsm.validations.EmailValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
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
