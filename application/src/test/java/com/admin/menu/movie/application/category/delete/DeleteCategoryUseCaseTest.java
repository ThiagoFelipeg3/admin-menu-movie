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

	/**
	 * Como descrito no curso este é um teste inútil isso porque o categoryGateway não retorna nada
	 * e eu não quero que faça nada quando passar um ID invalido ou que não exista.
	 *
	 * Mas é interessante manter para que caso ocorra um cenário parecido já tenha um teste que cobre
	 * ou que apresente oque eu espero.
	 */
	@Test
	public void givenAInvalidId_whenCallsDeleteCategory_shouldBeOK() {
		CategoryID id = CategoryID.from("123");

		doNothing()
				.when(categoryGateway).deleteById(eq(id));

		Assertions.assertDoesNotThrow(() -> useCase.execute(id.getValue()));

		Mockito.verify(categoryGateway, times(1)).deleteById(eq(id));
	}

	@Test
	public void givenAValidId_whenGatewayThrowsException_shouldReturnException() {
		Category category = Category.newCategory("Movies", "Any Description", true);
		CategoryID id = category.getId();

		doThrow(new IllegalStateException("Gateway error"))
				.when(categoryGateway).deleteById(eq(id));

		Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(id.getValue()));

		Mockito.verify(categoryGateway, times(1)).deleteById(eq(id));
	}
}
