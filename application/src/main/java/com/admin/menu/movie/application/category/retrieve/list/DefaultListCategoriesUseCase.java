package com.admin.menu.movie.application.category.retrieve.list;

import com.admin.menu.movie.application.UseCase;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategorySearchQuery;
import com.admin.menu.movie.domain.pagination.Pagination;

import java.util.Objects;

public class DefaultListCategoriesUseCase
		extends UseCase<CategorySearchQuery, Pagination<CategoryListOutput>> {

	private final CategoryGateway categoryGateway;

	public DefaultListCategoriesUseCase(final CategoryGateway categoryGateway) {
		this.categoryGateway = Objects.requireNonNull(categoryGateway);
	}

	@Override
	public Pagination<CategoryListOutput> execute(CategorySearchQuery query) {
		return this.categoryGateway.findAll(query)
				.map(CategoryListOutput::from);
	}
}
