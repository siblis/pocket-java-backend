package ru.geekbrains.pocket.backend.util.validation;

import ru.geekbrains.pocket.backend.domain.SystemUser;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches, Object> {

    @Override
    public void initialize(final PasswordMatches constraintAnnotation) {
        //
    }

    @Override
    public boolean isValid(final Object obj, final ConstraintValidatorContext context) {
        final SystemUser user = (SystemUser) obj;
        return user.getPassword().equals(user.getMatchingPassword());
    }

}
