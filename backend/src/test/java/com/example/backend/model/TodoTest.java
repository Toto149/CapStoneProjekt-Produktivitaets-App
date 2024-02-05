package com.example.backend.model;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.*;

class TodoTest {

    @Test
    void testCompareTo_shouldReturnMinus1_whenElementComparedIsLesserInOrder() {
        //GIVEN
        Todo td1 = new Todo(
                "1",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2024,1,22,2,2,2),
                Importance.MODERATELY_IMPORTANT,
                new CompletionTime(10, TimeUnit.HOURS)

        );
        Todo td2 = new Todo(
                "2",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2024,1,21,1,1,1),
                Importance.QUITE_IMPORTANT,
                new CompletionTime(9,TimeUnit.HOURS)
        );
        int expected = 1;
        //WHEN
        int actual = td1.compareTo(td2);
        //THEN
        assertEquals(expected,actual);
    }
    @Test
    void testCompareTo_shouldReturn0_whenElementsComparedAreEqual() {
        //GIVEN
        Todo td1 = new Todo(
                "1",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2024,1,22,2,2,2),
                Importance.MODERATELY_IMPORTANT,
                new CompletionTime(10, TimeUnit.HOURS)

        );
        Todo td2 = new Todo(
                "2",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2024,1,22,2,2,2),
                Importance.MODERATELY_IMPORTANT,
                new CompletionTime(10,TimeUnit.HOURS)
        );
        int expected = 0;
        //WHEN
        int actual = td1.compareTo(td2);
        //THEN
        assertEquals(expected,actual);
    }
    @Test
    void testCompareTo_shouldReturnMinus1_whenElementsComparedAreEqualApartFromTimeToComplete() {
        //GIVEN
        Todo td1 = new Todo(
                "1",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2024,1,22,2,2,2),
                Importance.MODERATELY_IMPORTANT,
                new CompletionTime(9, TimeUnit.HOURS)

        );
        Todo td2 = new Todo(
                "2",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2024,1,22,2,2,2),
                Importance.MODERATELY_IMPORTANT,
                new CompletionTime(10,TimeUnit.HOURS)
        );
        int expected = -1;
        //WHEN
        int actual = td1.compareTo(td2);
        //THEN
        assertEquals(expected,actual);
    }
}