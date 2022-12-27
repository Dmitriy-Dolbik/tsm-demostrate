package ru.karod.tsm.annotations;

import ru.karod.tsm.validations.PasswordValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = PasswordValidator.class)
@Documented
public @interface ValidPassword {
    String message();

    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
