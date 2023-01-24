package com.company.validator;

import com.company.domains.AuthUser;
import org.springframework.beans.factory.annotation.Autowired;
import com.company.repository.AuthRepository;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Optional;

public class UniqueEmailValidator implements ConstraintValidator<UniqueUsername, String> {

    @Autowired
    private AuthRepository repository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        Optional<AuthUser> byUsername = repository.findByUsername(value);
        return byUsername.isEmpty();
    }

}
