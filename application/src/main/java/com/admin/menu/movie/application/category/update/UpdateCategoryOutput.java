package com.admin.menu.movie.application.category.update;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryID;

public record UpdateCategoryOutput(
		CategoryID id
) {
	public static UpdateCategoryOutput from(final Category category) {
		return new UpdateCategoryOutput(category.getId());
	}
}
