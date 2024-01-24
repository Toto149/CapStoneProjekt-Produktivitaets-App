package com.example.backend.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import com.example.backend.model.LocalDateFormatter.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class LocalDateFormatterTest {

    @Test
    void parseD() {
        //GIVEN
        String datetime = "12.12.2024 12:34-56";
        LocalDateTime expected = LocalDateTime.of(2024,12,12,12,34,56);
        //WHEN
        LocalDateTime actual = LocalDateTime.parse(datetime,DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        //THEN
        assertEquals(expected, actual);
    }

    @Test
    void print() {

    }
}