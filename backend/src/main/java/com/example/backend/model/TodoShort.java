package com.example.backend.model;

import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "todoShort")
public record TodoShort(String id,
                        String title,
                        String description,

                        LocalDateTime startDate,

                        LocalDateTime deadline) {
}
