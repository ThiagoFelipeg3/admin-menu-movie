package com.admin.menu.movie.application.category.create;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.validation.handler.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {

	private final CategoryGateway categoryGateway;

	public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
		this.categoryGateway = Objects.requireNonNull(categoryGateway);
	}

	@Override
	public CreateCategoryOutput execute(final CreateCategoryCommand command) {
		final var category = Category.newCategory(
				command.name(),
				command.description(),
				command.isActive()
		);
		category.validate(new ThrowsValidationHandler());

		return CreateCategoryOutput.from(this.categoryGateway.create(category));
	}
}
