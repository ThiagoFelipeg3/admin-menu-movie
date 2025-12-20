package com.admin.menu.movie.infrastructure.category.pesistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<CategoryJpaEntity, String> {
}
