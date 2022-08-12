package com.example.axolotl.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = FieldEqualsValidation.class)
@Target( { ElementType.METHOD, ElementType.FIELD })
@Retention(RetentionPolicy.RUNTIME)
public @interface FieldEquals {
    String message() default "{FieldEquals}";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
