package ru.geekbrains.pocket.backend.util.validation.unused;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class SpecCodeValidator implements ConstraintValidator<SpecCode, String> {
    private String specCodePrefix;

    @Override
    public void initialize(SpecCode constraintAnnotation) {
        specCodePrefix = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean result = false;
        if (value != null) {
            result = value.startsWith(specCodePrefix);
        } else {
            result = true;
        }
        return result;
    }
}
