package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;


import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TodoServiceTest {
    TodoRepository todoRepo = mock(TodoRepository.class);
    IdService idService = mock(IdService.class);
    TodoService service = new TodoService(todoRepo,idService);


    @Test
    void testTransformStringToCompletionTime_when100_hours_shouldReturn100hHours(){
        //GIVEN
        CompletionTime expected = new CompletionTime(100, TimeUnit.HOURS);
        //WHEN
        CompletionTime actual = TodoService.transformStringToCompletionTime("100 hours");
        //THEN
        assertEquals(expected,actual);
    }
    @Test
    void testGetAllTodos_whenCalled_thenReturnsAllPersons() {
        //GIVEN
        System.out.println(new CompletionTime(1,TimeUnit.HOURS));
        List<Todo> expected = List.of(new Todo(
                        "123",
                        "Test",
                        "Alles klar",
                LocalDateTime.of(2024,1,16,12,34,56),
                LocalDateTime.of(2024,1,16,12,34,56),
                Importance.BARELY_IMPORTANT,
                new CompletionTime(100, TimeUnit.HOURS)));

         when(todoRepo.findAll()).thenReturn(List.of(new TodoDB(
                 "123",
                 "Test",
                 "Alles klar",
                 "2024-01-16T12:34:56",
                 "2024-01-16T12:34:56",
                 Importance.BARELY_IMPORTANT.name(),
                 "100 hours"
         )));

        //WHEN
        List<Todo> actual = service.getAllTodos();

        //THEN
        assertEquals(expected, actual);

    }

    @Test
    void testTransformTodo_whenCalled_thenReturnsTodoShort() {
        //GIVEN
        List<TodoDB> todoIntList =List.of( new TodoDB(
                "Test",
                "Test",
                "Test",
                "2024-12-12T12:12:12",
                "2024-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        ));
        List<Todo> expected = List.of(new Todo(
                "Test",
                "Test",
                "Test",
                LocalDateTime.of(2024,12,12,12,12,12),
                LocalDateTime.of(2024,12,12,12,12,12),
                Importance.BARELY_IMPORTANT,
                new CompletionTime(100,TimeUnit.HOURS)
        ));

        //WHEN
        List<Todo> actual = TodoService.transformTodoDBToTodo(todoIntList);

        //THEN
        assertEquals(actual,expected);


    }

    @Test
    void testDeleteTodo_whenCalledSuccessfully_shouldReturnTrue(){
        //GIVEN
        when(todoRepo.findAll()).thenReturn(Collections.singletonList(new TodoDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
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
                "2023-12-12T12:12:12",
                1,
                "100 hours"
        );
        Todo expected = new Todo(
                "1",
                "Test2",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2023,12,12,12,12,12),
                Importance.BARELY_IMPORTANT,
                new CompletionTime(100,TimeUnit.HOURS)
        );
        when(todoRepo.save(new TodoDB(
                "1",
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        ))).thenReturn(new TodoDB(
                "1",
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        ));
        //WHEN
        Todo actual = service.updateTodo(id,todo);

        //THEN
        verify(todoRepo).save(new TodoDB(
                "1",
                "Test2",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        ));
        assertEquals(expected,actual);


    }

    @Test
    void testSaveTodo_shouldReturnTodo_whenTodoIsSaved(){
        //GIVEN
        Todo expected = new Todo(
                "1",
                "Test",
                "Test",
                LocalDateTime.of(2023,12,12,12,12,12),
                LocalDateTime.of(2023,12,12,12,12,12),
                Importance.BARELY_IMPORTANT,
                new CompletionTime(100,TimeUnit.HOURS)
        );
        PostTodo todoToSave = new PostTodo(
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                1,
                "100 hours"
        );
        when(idService.generateId()).thenReturn("1");
        when(todoRepo.save(new TodoDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        ))).thenReturn(new TodoDB(
                        "1",
                        "Test",
                        "Test",
                        "2023-12-12T12:12:12",
                        "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
                ));
        //WHEN

        Todo actual = service.saveTodo(todoToSave);

        //THEN

        verify(idService).generateId();
        verify(todoRepo).save(new TodoDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        ));
        assertEquals(expected,actual);

    }

}