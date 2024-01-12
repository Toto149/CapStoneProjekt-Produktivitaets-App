package com.example.backend.service;

import com.example.backend.model.CompletionTime;
import com.example.backend.model.Importance;
import com.example.backend.model.Todo;
import com.example.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class TodoServiceTest {
    TodoRepository todoRepo = mock(TodoRepository.class);
    TodoService service = new TodoService(todoRepo);
    @Test
    void testGetAllTodos_whenCalled_thenReturnsAllPersons() {
        //GIVEN
        LocalDateTime time = LocalDateTime.now();
        List<Todo> expected = List.of(new Todo(
                        "123",
                        "Test",
                        "Alles klar",
                        time,
                        Importance.BARELY_IMPORTANT,
                        new CompletionTime(10, TimeUnit.MINUTES)
                ));

         when(todoRepo.findAll()).thenReturn(List.of(new Todo(
                 "123",
                 "Test",
                 "Alles klar",
                 time,
                 Importance.BARELY_IMPORTANT,
                 new CompletionTime(10, TimeUnit.MINUTES)
         )));

        //WHEN
        List<Todo> actual = service.getAllTodos();

        //THEN
        assertEquals(expected, actual);

    }
}