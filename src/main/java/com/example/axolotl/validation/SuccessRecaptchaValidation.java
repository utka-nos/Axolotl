package com.example.axolotl.validation;

import com.example.axolotl.service.RegistrationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@Component
public class SuccessRecaptchaValidation implements ConstraintValidator<SuccessRecaptcha, String> {
    @Override
    public void initialize(SuccessRecaptcha constraintAnnotation) {

    }

    @Autowired
    private RegistrationService registrationService;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return registrationService.checkReCaptcha(s);
    }
}
