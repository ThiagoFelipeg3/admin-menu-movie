package com.admin.menu.movie.domain.exceptions;

import com.admin.menu.movie.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStacktraceException {
    private final List<Error> errors;

    private DomainException(String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }

    public static DomainException with(final Error error) {
        return new DomainException(error.message(), List.of(error));
    }

    public List<Error> getErrors() {
        return errors;
    }
}
