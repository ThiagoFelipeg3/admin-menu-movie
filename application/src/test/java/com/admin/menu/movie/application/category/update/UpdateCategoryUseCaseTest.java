package com.admin.menu.movie.application.category.update;

import com.admin.menu.movie.domain.category.Category;
import com.admin.menu.movie.domain.category.CategoryGateway;
import com.admin.menu.movie.domain.category.CategoryID;
import com.admin.menu.movie.domain.exceptions.DomainException;
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

	@Test
	public void givenAInvalidCommand_whenCallsUpdateCategory_shouldReturnDomainException() {
		final var category =
				Category.newCategory("Film", null, true);
		final String expectedName = null;
		final var expectedDescription = "Any description";
		final var expectedIsActive = true;
		final var expectedErrorMessage = "'name' should not be null";
		final var expectedErrorCount = 1;
		final var expectedId = category.getId();

		final var command = UpdateCategoryCommand.with(
				expectedId.getValue(),
				expectedName,
				expectedDescription,
				expectedIsActive
		);

		when(categoryGateway.findById(eq(expectedId)))
				.thenReturn(Optional.of(category.clone()));

		final var notification = useCase.execute(command).getLeft();

		Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
		Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

		Mockito.verify(categoryGateway, times(0)).update(any());
	}

	@Test
	public void givenAValidInactivateCommand_whenCallsUpdateCategory_shouldReturnInactiveCategoryId() {
		final var category =
				Category.newCategory("Film", null, true);

		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = false;
		final var expectedId = category.getId();

		final var command = UpdateCategoryCommand.with(
				expectedId.getValue(),
				expectedName,
				expectedDescription,
				expectedIsActive
		);

		when(categoryGateway.findById(eq(expectedId)))
				.thenReturn(Optional.of(Category.with(category)));

		when(categoryGateway.update(any()))
				.thenAnswer(returnsFirstArg());

		Assertions.assertTrue(category.isActive());
		Assertions.assertNull(category.getDeletedAt());

		final var actualOutput = useCase.execute(command).get();

		Assertions.assertNotNull(actualOutput);
		Assertions.assertNotNull(actualOutput.id());

		Mockito.verify(categoryGateway, times(1)).findById(eq(expectedId));

		Mockito.verify(categoryGateway, times(1)).update(argThat(
				updatedCategory ->
						Objects.equals(expectedName, updatedCategory.getName())
								&& Objects.equals(expectedDescription, updatedCategory.getDescription())
								&& Objects.equals(expectedIsActive, updatedCategory.isActive())
								&& Objects.equals(expectedId, updatedCategory.getId())
								&& Objects.equals(category.getCreatedAt(), updatedCategory.getCreatedAt())
								&& category.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
								&& Objects.nonNull(updatedCategory.getDeletedAt())
		));
	}

	@Test
	public void givenAValidCommand_whenGatewayThrowsRandomException_shouldReturnAException() {
		final var category =
				Category.newCategory("Film", null, true);

		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = true;
		final var expectedId = category.getId();
		final var expectedErrorCount = 1;
		final var expectedErrorMessage = "Gateway error";

		final var aCommand = UpdateCategoryCommand.with(
				expectedId.getValue(),
				expectedName,
				expectedDescription,
				expectedIsActive
		);

		when(categoryGateway.findById(eq(expectedId)))
				.thenReturn(Optional.of(Category.with(category)));

		// Força o lançamento de uma exceção ao chamar o método updete do gateway
		when(categoryGateway.update(any()))
				.thenThrow(new IllegalStateException(expectedErrorMessage));

		final var notification = useCase.execute(aCommand).getLeft();

		Assertions.assertEquals(expectedErrorCount, notification.getErrors().size());
		Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());

		Mockito.verify(categoryGateway, times(1)).update(argThat(
				aUpdatedCategory ->
						Objects.equals(expectedName, aUpdatedCategory.getName())
								&& Objects.equals(expectedDescription, aUpdatedCategory.getDescription())
								&& Objects.equals(expectedIsActive, aUpdatedCategory.isActive())
								&& Objects.equals(expectedId, aUpdatedCategory.getId())
								&& Objects.equals(category.getCreatedAt(), aUpdatedCategory.getCreatedAt())
								&& category.getUpdatedAt().isBefore(aUpdatedCategory.getUpdatedAt())
								&& Objects.isNull(aUpdatedCategory.getDeletedAt())
		));
	}

	@Test
	public void givenACommandWithInvalidID_whenCallsUpdateCategory_shouldReturnNotFoundException() {
		final var expectedName = "Filmes";
		final var expectedDescription = "A categoria mais assistida";
		final var expectedIsActive = false;
		final var expectedId = "123";
		final var expectedErrorMessage = "Category with ID 123 was not found";

		final var aCommand = UpdateCategoryCommand.with(
				expectedId,
				expectedName,
				expectedDescription,
				expectedIsActive
		);

		when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
				.thenReturn(Optional.empty());

		final var actualException =
				Assertions.assertThrows(DomainException.class, () -> useCase.execute(aCommand));

		Assertions.assertEquals(expectedErrorMessage, actualException.getMessage());

		Mockito.verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));

		Mockito.verify(categoryGateway, times(0)).update(any());
	}
}
