package com.admin.menu.movie.application.category.delete;

import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategoryID;

import java.util.Objects;

public class DefaultDeleteCategoryUseCase extends DeleteCategoryUseCase {

	private final CategoryGateway categoryGateway;

	public DefaultDeleteCategoryUseCase(CategoryGateway categoryGateway) {
		this.categoryGateway = Objects.requireNonNull(categoryGateway);
	}

	@Override
	public void execute(String id) {
		this.categoryGateway.deleteById(CategoryID.from(id));
	}
}
