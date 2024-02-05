package com.example.backend.model;

public record PostTodo(String title,
                       String description,
                       String startDate,
                       String deadline,
                       int gradeOfImportance,
                       String timeToComplete
                       ) {
}
