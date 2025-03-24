package com.jjm.cleanspring.infrastructure.abstraction;

import jakarta.validation.*;

import java.util.Set;

public abstract class SelfValidating<T> {
    private Validator validator;

    protected SelfValidating() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    protected void validateSelf() {
        Set<ConstraintViolation<T>> violations = validator.validate((T) this);

        if (!violations.isEmpty()) {
            throw new ConstraintViolationException(violations);
        }
    }
}
