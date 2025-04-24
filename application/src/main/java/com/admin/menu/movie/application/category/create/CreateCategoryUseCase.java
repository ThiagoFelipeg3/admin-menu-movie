package com.admin.menu.movie.application.category.create;

import com.admin.menu.movie.application.UseCase;
import com.admin.menu.movie.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class CreateCategoryUseCase
		extends UseCase<CreateCategoryCommand, Either<Notification, CreateCategoryOutput>> {
}
