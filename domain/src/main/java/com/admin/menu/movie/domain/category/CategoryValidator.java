package com.admin.menu.movie.domain.category;

import com.admin.menu.movie.domain.validation.ValidationHandler;
import com.admin.menu.movie.domain.validation.Validator;
import com.admin.menu.movie.domain.validation.Error;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category, final ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if (this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' should not be null"));
        }
    }
}
