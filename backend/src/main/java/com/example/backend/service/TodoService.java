package com.example.backend.service;

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
    public List<TodoShort> getAllTodos() {
        List<TodoShortDB> todos = repo.findAll();
        return transformTodoShortDBToTodoShort(todos);

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
}
