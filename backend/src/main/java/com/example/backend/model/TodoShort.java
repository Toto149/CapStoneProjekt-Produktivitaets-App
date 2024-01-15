package com.example.backend.model;

import java.time.LocalDateTime;

public record TodoShort(String id,
                        String title,
                        String description,
                        LocalDateTime startDate,
                        LocalDateTime deadline) {
}
