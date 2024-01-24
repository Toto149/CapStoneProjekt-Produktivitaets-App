package com.example.backend.service;

import com.example.backend.model.PostTodo;
import com.example.backend.model.TodoShort;
import com.example.backend.model.TodoShortDB;
import com.example.backend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repo;
    private final IdService idService;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public List<TodoShort> getAllTodos() {
        List<TodoShortDB> todos = repo.findAll();
        return transformTodoShortDBToTodoShort(todos);

    }

    public boolean deleteTodo(String id){
        repo.deleteById(id);
        return !repo.existsById(id);

    }
    public static List<TodoShort> transformTodoShortDBToTodoShort(List<TodoShortDB> todos){
        return todos.stream().map(
                todoInter -> new TodoShort(
                            todoInter.id(),
                            todoInter.title(),
                            todoInter.description(),
                            LocalDateTime.parse(todoInter.startDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                            LocalDateTime.parse(todoInter.deadline(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)
                    )

        ).toList();
    }

    public TodoShort saveTodo(PostTodo todoToSave) {
        TodoShortDB todoSavedToDB = repo.save(new TodoShortDB(
                idService.generateId(),
                todoToSave.title(),
                todoToSave.description(),
                todoToSave.startDate().formatted(dateTimeFormatter),
                todoToSave.deadline().formatted(dateTimeFormatter)
        ));
        List<TodoShortDB> list = List.of(todoSavedToDB);
        return transformTodoShortDBToTodoShort(list).get(0);
    }

    public TodoShort updateTodo(String id, PostTodo todoToUpdate) {
        repo.deleteById(id);
        TodoShortDB todoToUpdateDB = repo.save(new TodoShortDB(id,
                todoToUpdate.title(),
                todoToUpdate.description(),
                todoToUpdate.startDate().formatted(dateTimeFormatter),
                todoToUpdate.deadline().formatted(dateTimeFormatter)));
        List<TodoShortDB> list = List.of(todoToUpdateDB);
        return transformTodoShortDBToTodoShort(list).get(0);
    }
}
