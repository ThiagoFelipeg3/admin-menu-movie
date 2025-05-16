package com.admin.menu.movie.application.category.delete;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {
	@InjectMocks
	private DefaultDeleteCategoryUseCase useCase;

	@Mock
	private CategoryGateway categoryGateway;

	@BeforeEach
	void cleanUp() {
		Mockito.reset(categoryGateway);
	}

	@Test
	public void givenAValidId_whenCallsDeleteCategory_shouldBeOK() {
		Category category = Category.newCategory("Movie", "Any Description", true);
		CategoryID id = category.getId();

		doNothing().when(categoryGateway).deleteById(eq(id));

		Assertions.assertDoesNotThrow(() -> useCase.execute(id.getValue()));

		Mockito.verify(categoryGateway, times(1)).deleteById(eq(id));
	}
}
