package com.company.validator;

import com.company.dto.UserCreateDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordMatchesValidator implements ConstraintValidator<PasswordMatches,Object> {
        @Override
        public void initialize(PasswordMatches constraintAnnotation) {
        }
        @Override
        public boolean isValid(Object obj, ConstraintValidatorContext context){
            UserCreateDTO user = (UserCreateDTO) obj;
            return user.getPassword().equals(user.getRepeatPassword());
        }
    }

