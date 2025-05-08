package com.admin.menu.movie.application.category.update;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UpdateCategoryUseCaseTest {
	@InjectMocks
	private DefaultUpdateCategoryUseCase useCase;

	@Mock
	private CategoryGateway categoryGateway;

	@Test
	public void givenAValidCommand_whenCallsUpdateCategory_shouldReturnCategoryId() {
		final var category =
				Category.newCategory("Film", null, true);

		final var expectedName = "Any Movie";
		final var expectedDescription = "Any description";
		final var expectedIsActive = true;
		final var expectedId = category.getId();

		final var command = UpdateCategoryCommand.with(
				expectedId.getValue(),
				expectedName,
				expectedDescription,
				expectedIsActive
		);

		/**
		 * Quando o CategoryGateway metodo findById for chamado com um expectedId igual(eq) a este
		 * então eu quero que retorna um Optional<category>
		 */
		when(categoryGateway.findById(eq(expectedId)))
				.thenReturn(Optional.of(category.clone()));

		/**
		 * Quando o CategoryGateway metodo update for chamado com qualquer coisa(any)
		 * então eu quero que responda com oque foi passado no primeiro argumento(returnsFirstArg)
		 */
		when(categoryGateway.update(any()))
				.thenAnswer(returnsFirstArg());

		final var actualOutput = useCase.execute(command).get();

		Assertions.assertNotNull(actualOutput);
		Assertions.assertNotNull(actualOutput.id());

		/**
		 * Verifica se o CategoryGateway foi chamado um unica vez e o meotod findById foi chamado com o expectedId igual(eq)
		 */
		Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

		/**
		 * Verifica se o CategoryGateway foi chamado uma unica vez e o update foi chamado com estes argumentos(argThat)
		 *
		 * Passamos uma lambda do java para verificar se os argumentos são igual utilizando Object equals
		 */
		Mockito.verify(categoryGateway, times(1)).update(argThat(
				updateCategory ->
						Objects.equals(expectedName, updateCategory.getName())
								&& Objects.equals(expectedDescription, updateCategory.getDescription())
								&& Objects.equals(expectedIsActive, updateCategory.isActive())
								&& Objects.equals(expectedId, updateCategory.getId())
								&& Objects.equals(category.getCreatedAt(), updateCategory.getCreatedAt())
								&& category.getUpdatedAt().isBefore(updateCategory.getUpdatedAt())
								&& Objects.isNull(updateCategory.getDeletedAt())
		));
	}
}
