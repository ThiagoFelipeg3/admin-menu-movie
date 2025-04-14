package com.admin.menu.movie.application.category.create;

import com.admin.menu.movie.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class CreateCategoryUseCaseTest {
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
		final CategoryGateway categoryGateway = Mockito.mock(CategoryGateway.class);

		when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

		final var useCase = new DefaultCreateCategoryUseCase(categoryGateway);

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
}
