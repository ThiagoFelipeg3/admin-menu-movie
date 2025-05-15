package com.admin.menu.movie.domain.category;

import com.admin.menu.movie.domain.AggregateRoot;
import com.admin.menu.movie.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {

    private String name;
    private String description;
    private boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID id,
            final String name,
            final String description,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(String name, String description, boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new Category(id, name, description, isActive, now, now, deletedAt);
    }

    public static Category with(
            CategoryID id,
            String name,
            String description,
            boolean isActive,
            Instant createdAt,
            Instant updatedAt,
            Instant deletedAt
    ) {
        return new Category(
                id,
                name,
                description,
                isActive,
                createdAt,
                updatedAt,
                deletedAt
        );
    }

    public static Category with(final Category category) {
        return with(
                category.getId(),
                category.name,
                category.description,
                category.isActive(),
                category.createdAt,
                category.updatedAt,
                category.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler handler) {
        new CategoryValidator(this, handler).validate();
    }

    public Category activate() {
        if (this.isActive()) {
            return this;
        }

        final var now = Instant.now();

        this.isActive = true;
        this.deletedAt = null;
        this.updatedAt = now;

        return this;
    }

    public Category deactivate() {
        if (!this.isActive()) {
            return this;
        }

        final var now = Instant.now();

        this.isActive = false;
        this.deletedAt = now;
        this.updatedAt = now;

        return this;
    }

    public Category update(String name, String description, boolean isActive) {
        if (isActive) {
            activate();
        } else {
            deactivate();
        }

        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();

        return this;
    }

    public CategoryID getId() {
        return id;
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

    public boolean isActive() {
        return isActive;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

	@Override
	public Category clone() {
		try {
			return(Category) super.clone();
		} catch (CloneNotSupportedException e) {
			throw new AssertionError();
		}
	}
}
