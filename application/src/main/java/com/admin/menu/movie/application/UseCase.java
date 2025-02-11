package com.admin.menu.movie.application;

import com.admin.menu.movie.domain.category.Category;

public class UseCase {
   public Category execute() {
       return Category.newCategory("Category UseCase", "", true);
   }
}
