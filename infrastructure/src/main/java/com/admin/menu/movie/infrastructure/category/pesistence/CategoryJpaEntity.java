package com.admin.menu.movie.infrastructure.category.pesistence;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryID;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.Instant;

@Entity
@Table(name = "categories")
public class CategoryJpaEntity {
	@Id
	public String id;

	@Column(name = "name", nullable = false)
	public String name;

	@Column(name = "description", length = 4000)
	public String description;

	@Column(name = "active", nullable = false)
	public boolean active;

	@Column(name = "createdAt", nullable = false, columnDefinition = "DATETIME(6)")
	public Instant createdAt;

	@Column(name = "updatedAt", nullable = false, columnDefinition = "DATETIME(6)")
	public Instant updatedAt;

	@Column(name = "deletedAt", nullable = true, columnDefinition = "DATETIME(6)")
	public Instant deletedAt;

	public CategoryJpaEntity() {

	}

	private CategoryJpaEntity(
			final String id,
			final String name,
			final String description,
			final boolean active,
			final Instant createdAt,
			final Instant updatedAt,
			final Instant deletedAt
	) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.active = active;
		this.createdAt = createdAt;
		this.updatedAt = updatedAt;
		this.deletedAt = deletedAt;
	}

	private static CategoryJpaEntity from(final Category category) {
		return new CategoryJpaEntity(
				category.getId().getValue(),
				category.getName(),
				category.getDescription(),
				category.isActive(),
				category.getCreatedAt(),
				category.getUpdatedAt(),
				category.getDeletedAt()
		);
	}

	public Category toAggregate() {
		return Category.with(
				CategoryID.from(getId()),
				getName(),
				getDescription(),
				isActive(),
				getCreatedAt(),
				getUpdatedAt(),
				getDeletedAt()
		);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Instant createdAt) {
		this.createdAt = createdAt;
	}

	public Instant getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(Instant updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Instant getDeletedAt() {
		return deletedAt;
	}

	public void setDeletedAt(Instant deletedAt) {
		this.deletedAt = deletedAt;
	}
}
