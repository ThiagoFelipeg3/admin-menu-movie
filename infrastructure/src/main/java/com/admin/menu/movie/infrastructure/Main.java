package com.admin.menu.movie.infrastructure;

import com.admin.menu.movie.application.UseCase;

public class Main {
    public static void main(String[] args) {
        System.out.println(new UseCase().execute());
    }
}