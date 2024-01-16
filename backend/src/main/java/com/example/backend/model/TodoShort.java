package com.example.backend.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
@Document(collection = "todoShort")
public record TodoShort(String id,
                        String title,
                        String description,
                        @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
                        LocalDateTime startDate,
                        @JsonFormat(pattern = "YYYY-MM-DD hh:mm:ss")
                        LocalDateTime deadline) {
}
