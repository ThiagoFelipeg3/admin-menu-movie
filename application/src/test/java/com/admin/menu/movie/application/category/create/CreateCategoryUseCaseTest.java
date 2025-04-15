package com.admin.menu.movie.application.category.create;

import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

	@InjectMocks
	private DefaultCreateCategoryUseCase useCase;

	@Mock
	private CategoryGateway categoryGateway;

	@Test
	public void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() {
		final var expectedName = "Any Movie";
		final var expectedDescription = "Any description";
		final var expectedIsActive = true;

		/**
		 * Aqui poderiamos passar um factory method, DTO ou qualquer coisa classe que criar um objeto,
		 * Neste caso vamos utiliar o padrão command que tbm tem esta responsabilidade de criar um objeto a apartir
		 * dos dados que esta sendo informado na entrada.
		 */
		final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

		when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

		final var actualOutput = useCase.execute(command);

		Assertions.assertNotNull(actualOutput);
		Assertions.assertNotNull(actualOutput.id());

		Mockito.verify(categoryGateway, times(1))
				.create(argThat(category -> {
					return Objects.equals(expectedName, category.getName())
							&& Objects.equals(expectedDescription, category.getDescription())
							&& Objects.equals(expectedIsActive, category.isActive())
							&& Objects.nonNull(category.getId())
							&& Objects.nonNull(category.getCreatedAt())
							&& Objects.nonNull(category.getUpdatedAt())
							&& Objects.isNull(category.getDeletedAt());
				}));
	}

	@Test
	public void givenAInvalidCommand_whenCallsCreateCategory_shouldReturnDomainException() {
		final String expectedName = null;
		final var expectedDescription = "Any description";
		final var expectedIsActive = true;
		final var expectedErrorMessage = "'name' should not be null";
		final var expectedErrorCount = 0;

		final var command = CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

		final var actualError = Assertions.assertThrows(DomainException.class, () -> useCase.execute(command));

		Assertions.assertEquals(expectedErrorMessage, actualError.getMessage());

		Mockito.verify(categoryGateway, times(expectedErrorCount)).create(any());
	}
}
