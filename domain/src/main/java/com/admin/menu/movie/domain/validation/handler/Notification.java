package com.admin.menu.movie.domain.validation.handler;

import com.admin.menu.movie.domain.exceptions.DomainException;
import com.admin.menu.movie.domain.validation.Error;
import com.admin.menu.movie.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {
	private final List<Error> errors;

	private Notification(final List<Error> errors) {
		this.errors = errors;
	}

	public static Notification create() {
		return new Notification(new ArrayList<>());
	}

	public static Notification create(final Error error) {
		return (Notification) create().append(error);
	}

	public static Notification create(final Throwable t) {
		return create(new Error(t.getMessage()));
	}

	@Override
	public ValidationHandler append(Error error) {
		this.errors.add(error);
		return this;
	}

	@Override
	public ValidationHandler append(ValidationHandler handler) {
		this.errors.addAll(handler.getErrors());
		return this;
	}

	@Override
	public ValidationHandler validate(Validation validation) {
		try {
			validation.validate();
		} catch(DomainException error) {
			this.errors.addAll(error.getErrors());
		} catch(Throwable t) {
			this.errors.add(new Error(t.getMessage()));
		}

		return null;
	}

	@Override
	public List<Error> getErrors() {
		return this.errors;
	}
}
