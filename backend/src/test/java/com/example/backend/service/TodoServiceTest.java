package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;


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
        List<TodoShort> expected = List.of(new TodoShort(
                        "123",
                        "Test",
                        "Alles klar",
                LocalDateTime.of(2024,1,16,12,34,56),
                LocalDateTime.of(2024,1,16,12,34,56)
                ));

         when(todoRepo.findAll()).thenReturn(List.of(new TodoShortInternalDTO(
                 "123",
                 "Test",
                 "Alles klar",
                 "2024-01-16T12:34:56",
                 "2024-01-16T12:34:56"
         )));

        //WHEN
        List<TodoShort> actual = service.getAllTodos();

        //THEN
        assertEquals(expected, actual);

    }

}