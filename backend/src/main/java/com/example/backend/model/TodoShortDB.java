package com.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "todoShort")
public record TodoShortDB(
        String id,
        String title,
        String description,
        String startDate,
        String deadline
){
}
