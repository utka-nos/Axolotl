package com.example.axolotl.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = SuccessRecaptchaValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface SuccessRecaptcha {
    String message() default "{SuccessRecaptcha}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
