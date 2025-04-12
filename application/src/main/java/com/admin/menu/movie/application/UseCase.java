package com.admin.menu.movie.application;

import com.admin.menu.movie.domain.category.Category;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN in);
}
