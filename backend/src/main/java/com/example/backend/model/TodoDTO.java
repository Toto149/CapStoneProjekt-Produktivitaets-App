package com.example.backend.model;

import java.time.LocalDateTime;

public record TodoDTO(String id,
                      String description,
                      LocalDateTime deadline) {
}
