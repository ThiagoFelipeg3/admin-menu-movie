package com.admin.menu.movie.domain.category;

import com.admin.menu.movie.domain.exceptions.DomainException;
import com.admin.menu.movie.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class CategoryTest {

    @Test
    public void givenAValidParams_whenCallNewCategory_thenInstantiateACategory() {
        final var name = "Any Movie Name";
        final var description = "Any Description";
        final var isActive = true;

        final var category = Category.newCategory(name, description, isActive);

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(name, category.getName());
        Assertions.assertEquals(description, category.getDescription());
        Assertions.assertEquals(isActive, category.isActive());

        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Deve disparar um erro quando passar um nome nulo")
    public void shouldThrowAnExceptionWhenPassingANullName() {
        final var description = "Any Description";
        final var isActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be null";

        final var actualCategory = Category.newCategory(null, description, isActive);
        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    @DisplayName("Deve disparar um erro quando passar um nome vazio")
    public void shouldThrowAnExceptionWhenPassingAEmptyName() {
        final var isActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' should not be empty";

        final var actualCategory = Category.newCategory(" ", "Any Description", isActive);
        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    @DisplayName("Deve disparar um erro quando passar um nome com menos de 3 caracteres")
    public void shouldThrowAnExceptionWhenPassingANameLengthLessThan3() {
        final var isActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualCategory = Category.newCategory("Fi ", "Any Description", isActive);
        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    @DisplayName("Deve disparar um erro quando passar um nome maior que 255 caracteres")
    public void shouldThrowAnExceptionWhenPassingANameLengthMoreThan255() {
        final var expetedName = """
                Lorem ipsum dolor sit amet, consectetur adipiscing elit. Cras vestibulum luctus mi, at semper elit pharetra eget.
                Curabitur fermentum maximus lacinia. Suspendisse potenti. Sed ut placerat ex. Sed eu gravida dolor, non feugiat enim.
                Ut sit amet tellus sit amet nibh bibendum facilisis.
                """;
        final var isActive = true;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must be between 3 and 255 characters";

        final var actualCategory = Category.newCategory(expetedName, "Any Description", isActive);
        final var actualException =
                Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(expectedErrorCount, actualException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, actualException.getErrors().getFirst().message());

    }

    @Test
    public void shouldBeFineWhenYouGiveAnEmptyDescription() {
        final var name = "Any name Movie";
        final var description = " ";
        final var isActive = true;
        final var category = Category.newCategory(name, description, isActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(name, category.getName());
        Assertions.assertEquals(description, category.getDescription());
        Assertions.assertEquals(isActive, category.isActive());

        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    public void shouldBeFineWhenYouPassTheIsActiveFalseParameter() {
        final var name = "Any name Movie";
        final var description = "Any description movie";
        final var isActive = false;
        final var category = Category.newCategory(name, description, isActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        Assertions.assertNotNull(category);
        Assertions.assertNotNull(category.getId());
        Assertions.assertEquals(name, category.getName());
        Assertions.assertEquals(description, category.getDescription());
        Assertions.assertEquals(isActive, category.isActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    public void shouldDisableACategory() {
        final var name = "Any name Movie";
        final var description = "Any description movie";
        final var isActive = true;
        final var category = Category.newCategory(name, description, isActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        Assertions.assertTrue(category.isActive());
        Assertions.assertNull(category.getDeletedAt());

        final var actualCategory = category.deactivate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }

    @Test
    public void shouldActiveACategory() {
        final var name = "Any name Movie";
        final var description = "Any description movie";
        final var isActive = false;
        final var category = Category.newCategory(name, description, isActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();
        final var deletedAt = category.getDeletedAt();

        Assertions.assertFalse(category.isActive());
        Assertions.assertEquals(deletedAt, createdAt);

        final var actualCategory = category.activate();

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));

        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void shouldUpdateAnExistingAndValidCategory() {
        final var name = "Any Name Movie Updated";
        final var description = "Any description movie updated";
        final var isActive = true;

        final var category =
                Category.newCategory("Any Name Movie", "Any description movie", isActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        final var actualCategory = category.update(name, description, isActive);

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertTrue(actualCategory.isActive());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    public void shouldDisableAnActiveCategoryWhenUpdating() {
        final var name = "Any Name Movie Updated";
        final var description = "Any description movie updated";
        final var isActive = true;

        final var category =
                Category.newCategory("Any Name Movie", "Any description movie", isActive);

        Assertions.assertDoesNotThrow(() -> category.validate(new ThrowsValidationHandler()));

        final var createdAt = category.getCreatedAt();
        final var updatedAt = category.getUpdatedAt();

        Assertions.assertNull(category.getDeletedAt());

        final var actualCategory = category.update(name, description, false);

        Assertions.assertFalse(actualCategory.isActive());
        Assertions.assertNotNull(actualCategory.getDeletedAt());

        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(category.getId(), actualCategory.getId());
        Assertions.assertEquals(name, actualCategory.getName());
        Assertions.assertEquals(description, actualCategory.getDescription());
        Assertions.assertEquals(createdAt, actualCategory.getCreatedAt());
        Assertions.assertTrue(actualCategory.getUpdatedAt().isAfter(updatedAt));

    }
}





