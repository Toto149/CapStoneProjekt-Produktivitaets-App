package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {
    TodoRepository todoRepo = mock(TodoRepository.class);
    IdService idService = mock(IdService.class);
    TodoService service = new TodoService(todoRepo,idService);


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

    @Test
    void testDeleteTodo_whenCalledSuccessfully_shouldReturnTrue(){
        //GIVEN
        when(todoRepo.findAll()).thenReturn(Collections.singletonList(new TodoShortDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        )));
        when(todoRepo.existsById("1")).thenReturn(false);
        //WHEN
        boolean actual = service.deleteTodo("1");
        //THEN
        verify(todoRepo).deleteById("1");
        assertTrue(actual);
    }
    @Test
    void testUpdateTodo_whenCalledOnTodoWithTitleTest_shouldReturnTodoWithTitleTest2() throws NullPointerException{
        //GIVEN
        String id = "1";
        PostTodo todo = new PostTodo(
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        );
        TodoShort expected = new TodoShort(
                "1",
                "Test2",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2023,12,12,12,12,12)
        );
        when(todoRepo.save(new TodoShortDB(
                "1",
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        ))).thenReturn(new TodoShortDB(
                "1",
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        ));
        //WHEN
        TodoShort actual = service.updateTodo(id,todo);

        //THEN
        verify(todoRepo).deleteById(id);
        verify(todoRepo).save(new TodoShortDB(
                "1",
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        ));
        assertEquals(expected,actual);


    }

    @Test
    void testSaveTodo_shouldReturnTodo_whenTodoIsSaved(){
        //GIVEN
        TodoShort expected = new TodoShort(
                "1",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2023,12,12,12,12,12)
        );
        PostTodo todoToSave = new PostTodo(
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        );
        when(idService.generateId()).thenReturn("1");
        when(todoRepo.save(new TodoShortDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        ))).thenReturn(new TodoShortDB(
                        "1",
                        "Test",
                        "Test",
                        "2023-12-12T12:12:12",
                        "2023-12-12T12:12:12"
                ));
        //WHEN

        TodoShort actual = service.saveTodo(todoToSave);

        //THEN

        verify(idService).generateId();
        verify(todoRepo).save(new TodoShortDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
        ));
        assertEquals(expected,actual);

    }

}