package com.admin.menu.movie.application.category.retrieve.get;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryID;

import java.time.Instant;

public record CategoryOutput(
		CategoryID id,
		String name,
		String description,
		boolean isActive,
		Instant createdAt,
		Instant updatedAt,
		Instant deletedAt
) {
	static CategoryOutput from(final Category category) {
		return new CategoryOutput(
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
