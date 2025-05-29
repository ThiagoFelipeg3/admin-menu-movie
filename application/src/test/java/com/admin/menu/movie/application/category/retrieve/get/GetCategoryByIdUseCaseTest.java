package com.admin.menu.movie.application.category.retrieve;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

	@InjectMocks
	private DefaultGetCategoryByIdUseCase useCase;

	@Mock
	private CategoryGateway categoryGateway;

	@Override
	protected List<Object> getMocks() {
		return List.of(categoryGateway);
	}

	@Test
	public void givenAValidId_whenCallsGetCategory_shouldReturnCategory() {
		final var expectedName = "Movie";
		final var expectedDescription = "Any Description";
		final var expectedIsActive = true;

		final var category =
				Category.newCategory(expectedName, expectedDescription, expectedIsActive);

		final var expectedId = category.getId();

		when(categoryGateway.findById(eq(expectedId)))
				.thenReturn(Optional.of(category.clone()));

		final var actualCategory = useCase.execute(expectedId.getValue());

		Assertions.assertEquals(expectedId, actualCategory.id());
		Assertions.assertEquals(expectedName, actualCategory.name());
		Assertions.assertEquals(expectedDescription, actualCategory.description());
		Assertions.assertEquals(expectedIsActive, actualCategory.isActive());
		Assertions.assertEquals(category.getCreatedAt(), actualCategory.createdAt());
		Assertions.assertEquals(category.getUpdatedAt(), actualCategory.updatedAt());
		Assertions.assertEquals(category.getDeletedAt(), actualCategory.deletedAt());
	}
}
