package com.example.backend.model;

import java.time.LocalDateTime;

public record TodoShort(String id,
                        String description,
                        LocalDateTime deadline) {
}
