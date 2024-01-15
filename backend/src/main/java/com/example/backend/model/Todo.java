package com.example.backend.model;

import java.time.LocalDateTime;

public record Todo (String id,
                    String title,
                    String description,
                    LocalDateTime startDate,
                    LocalDateTime deadline,
                    Importance gradeOfImportance,
                    CompletionTime timeToComplete) {
}
