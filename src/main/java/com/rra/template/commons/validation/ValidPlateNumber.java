package com.rra.template.commons.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RwandaPlateNumberValidator.class)
@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidPlateNumber {
    String message() default "Invalid Rwanda plate number";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
