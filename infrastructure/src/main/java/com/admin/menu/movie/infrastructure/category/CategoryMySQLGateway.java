package com.admin.menu.movie.infrastructure.category;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategoryID;
import com.admin.menu.movie.domain.category.CategorySearchQuery;
import com.admin.menu.movie.domain.pagination.Pagination;
import com.admin.menu.movie.infrastructure.category.pesistence.CategoryJpaEntity;
import com.admin.menu.movie.infrastructure.category.pesistence.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryMySQLGateway implements CategoryGateway {

	private final CategoryRepository repository;

	public CategoryMySQLGateway(final CategoryRepository repository) {
		this.repository = repository;
	}

	@Override
	public Category create(Category category) {
		return repository.save(CategoryJpaEntity.from(category)).toAggregate();
	}

	@Override
	public void deleteById(CategoryID id) {

	}

	@Override
	public Optional<Category> findById(CategoryID id) {
		return Optional.empty();
	}

	@Override
	public Category update(Category category) {
		return null;
	}

	@Override
	public Pagination<Category> findAll(CategorySearchQuery query) {
		return null;
	}
}
