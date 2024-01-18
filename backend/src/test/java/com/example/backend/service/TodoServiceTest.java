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
        List<TodoShort> expected = List.of(new TodoShort(
                        "123",
                        "Test",
                        "Alles klar",
                LocalDateTime.of(2024,1,16,12,34,56),
                LocalDateTime.of(2024,1,16,12,34,56)
                ));

         when(todoRepo.findAll()).thenReturn(List.of(new TodoShortDB(
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

    @Test
    void testTransformTodo_whenCalled_thenReturnsTodoShort() {
        //GIVEN
        List<TodoShortDB> todoIntList =List.of( new TodoShortDB(
                "Test",
                "Test",
                "Test",
                "2024-12-12T12:12:12",
                "2024-12-12T12:12:12"
        ));
        List<TodoShort> expected = List.of(new TodoShort(
                "Test",
                "Test",
                "Test",
                LocalDateTime.of(2024,12,12,12,12,12),
                LocalDateTime.of(2024,12,12,12,12,12)
        ));

        //WHEN
        List<TodoShort> actual = TodoService.transformTodoShortDBToTodoShort(todoIntList);

        //THEN
        assertEquals(actual,expected);


    }

}