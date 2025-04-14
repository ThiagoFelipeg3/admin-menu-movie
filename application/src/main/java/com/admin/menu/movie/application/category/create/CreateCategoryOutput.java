package com.admin.menu.movie.application.category.create;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryID;

public record CreateCategoryOutput(
		CategoryID id
) {

	public static CreateCategoryOutput from(Category category) {
		return new CreateCategoryOutput(category.getId());
	}
}
