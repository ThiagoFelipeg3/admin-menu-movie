package com.admin.menu.movie.application.category.update;

import com.admin.menu.movie.application.UseCase;
import com.admin.menu.movie.domain.validation.handler.Notification;
import io.vavr.control.Either;

public abstract class UpdateCategoryUseCase
		extends UseCase<UpdateCategoryCommand, Either<Notification, UpdateCategoryOutput>> {
}
