package com.admin.menu.movie.application.category.retrieve.get;

import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategoryID;
import com.admin.menu.movie.domain.exceptions.DomainException;
import com.admin.menu.movie.domain.validation.Error;

import java.util.Objects;
import java.util.function.Supplier;

public class DefaultGetCategoryByIdUseCase extends GetCategoryByIdUseCase {

	private final CategoryGateway categoryGateway;

	public DefaultGetCategoryByIdUseCase(CategoryGateway categoryGateway) {
		this.categoryGateway = Objects.requireNonNull(categoryGateway);
	}

	@Override
	public CategoryOutput execute(String id) {
		CategoryID categoryId = CategoryID.from(id);

		return this.categoryGateway.findById(categoryId)
				.map(CategoryOutput::from)
				.orElseThrow(notFound(categoryId));
	}

	private static Supplier<DomainException> notFound(final CategoryID id) {
		return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
	}
}
