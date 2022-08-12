package com.example.axolotl.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FieldEqualsValidation implements ConstraintValidator<FieldEquals, String> {

    private static String value = null;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (value != null) {
            if (value.equals(s)) {
                value = null;
                return true;
            } else
                value = null;
                return false;
        }
        else {
            value = s;
        }
        return true;
    }

    @Override
    public void initialize(FieldEquals constraintAnnotation) {

    }
}
