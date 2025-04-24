package com.admin.menu.movie.application.category.create;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.validation.handler.Notification;

import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

	private final CategoryGateway categoryGateway;

	public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
		this.categoryGateway = Objects.requireNonNull(categoryGateway);
	}

	@Override
	public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand command) {
		final var category = Category.newCategory(
				command.name(),
				command.description(),
				command.isActive()
		);
		final Notification notification = Notification.create();
		category.validate(notification);

		return notification.hasError() ? API.Left(notification) : create(category);
	}

	public Either<Notification, CreateCategoryOutput> create(Category category) {
		/**
		 * Particulamente não gostei muito desta utilização do Vavr somente pelo benefício programação funcional
		 *
		 * Utilizando o Either do Vavr podemo retornar um ou outro utilizando generics type do proprio java
		 * oque ele esta fazendo aqui é um try catch comum porém transformando para Either e utilizando o bimap
		 * para caso de erro ou caso de sucesso
		 *
		 * Left => error Notification
		 * Right => sucesso CreateCategoryOutput
		 */
		return API.Try(() -> this.categoryGateway.create(category))
				.toEither()
				.bimap(Notification::create, CreateCategoryOutput::from);
	}
}
