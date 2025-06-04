package com.admin.menu.movie.application.category.retrieve.list;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryID;

import java.time.Instant;

public record CategoryListOutput(
		CategoryID id,
		String name,
		String description,
		boolean isActive,
		Instant createdAt,
		Instant updatedAt,
		Instant deletedAt
) {
	public static CategoryListOutput from(Category category) {
		return new CategoryListOutput(
				category.getId(),
				category.getName(),
				category.getDescription(),
				category.isActive(),
				category.getCreatedAt(),
				category.getUpdatedAt(),
				category.getDeletedAt()
		);
	}
}
