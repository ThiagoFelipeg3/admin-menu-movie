package com.admin.menu.movie.application.category.update;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategoryID;
import com.admin.menu.movie.domain.exceptions.DomainException;
import com.admin.menu.movie.domain.validation.Error;
import com.admin.menu.movie.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {

	private final CategoryGateway categoryGateway;

	public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
		this.categoryGateway = Objects.requireNonNull(categoryGateway);
	}

	@Override
	public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryCommand command) {
		final CategoryID id = CategoryID.from(command.id());
		final var name = command.name();
		final var description = command.description();
		final var isActive = command.isActive();

		final Category category = this.categoryGateway.findById(id)
				.orElseThrow(notFound(id));
		final Notification notification = Notification.create();

		category.update(name, description, isActive).validate(notification);

		return notification.hasError() ? API.Left(notification) : update(category);
	}

	private Either<Notification, UpdateCategoryOutput> update(Category category) {
		return API.Try(() -> this.categoryGateway.update(category))
				.toEither()
				.bimap(Notification::create, UpdateCategoryOutput::from);
	}

	private static Supplier<DomainException> notFound(final CategoryID id) {
		return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
	}
}
