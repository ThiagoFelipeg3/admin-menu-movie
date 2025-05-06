package com.admin.menu.movie.application;

public abstract class UseCase<IN, OUT> {
    public abstract OUT execute(IN in);
}
