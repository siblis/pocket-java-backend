package ru.geekbrains.pocket.backend.util.validation.unused;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = SpecCodeValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface SpecCode {
    String value() default "GEEK";

    String message() default "incorrect special code";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
