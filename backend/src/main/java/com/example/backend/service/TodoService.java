package com.example.backend.service;

import com.example.backend.model.*;
import com.example.backend.repository.TodoRepository;
import com.example.backend.model.Importance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repo;
    private final IdService idService;
    DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;
    public List<Todo> getAllTodos() {
        List<TodoDB> todos = repo.findAll();
        List<Todo> todos1 = transformTodoDBToTodo(todos);
        List<Todo> sortableTodos = new ArrayList<>(todos1);
        sortableTodos.sort(Todo::compareTo);

        return sortableTodos;
    }

    public boolean deleteTodo(String id){
        repo.deleteById(id);
        return !repo.existsById(id);

    }

    private String transformIntGradeOfImportanceToString(int gradeOfImportance){

        return switch (gradeOfImportance) {
            case 1 -> "BARELY_IMPORTANT";
            case 2 -> "MODERATELY_IMPORTANT";
            case 3 -> "IMPORTANT";
            case 4 -> "QUITE_IMPORTANT";
            case 5 -> "VERY_IMPORTANT";
            default -> throw new IllegalArgumentException("Must be a integer between 1 and 5 inclusively");
        };
    }
    public Todo saveTodo(PostTodo todoToSave) {
        TodoDB todoSavedToDB = repo.save(
                new TodoDB(
                idService.generateId(),
                todoToSave.title(),
                todoToSave.description(),
                todoToSave.startDate().formatted(dateTimeFormatter),
                todoToSave.deadline().formatted(dateTimeFormatter),
                transformIntGradeOfImportanceToString(todoToSave.gradeOfImportance()),
                todoToSave.timeToComplete()
        ));
        List<TodoDB> list = List.of(todoSavedToDB);
        return transformTodoDBToTodo(list).get(0);
    }

    public Todo updateTodo(String id, PostTodo todoToUpdate) {

        TodoDB todoToUpdateDB = repo.save(new TodoDB(
                id,
                todoToUpdate.title(),
                todoToUpdate.description(),
                todoToUpdate.startDate().formatted(dateTimeFormatter),
                todoToUpdate.deadline().formatted(dateTimeFormatter),
                transformIntGradeOfImportanceToString(todoToUpdate.gradeOfImportance()),
                todoToUpdate.timeToComplete()
                ));
        List<TodoDB> list = List.of(todoToUpdateDB);
        return transformTodoDBToTodo(list).get(0);
    }
    public static CompletionTime transformStringToCompletionTime(String timeString){
        //The String contains of the int Value and the unit like "100 hours", "100 minutes" etc
        String[] timeAndUnitInArray = timeString.split(" ");
        int amount = Integer.parseInt(timeAndUnitInArray[0]);
        TimeUnit unit = TimeUnit.valueOf("HOURS");
        return new CompletionTime(amount,unit);
    }
    public static List<Todo> transformTodoDBToTodo(List<TodoDB> todos){
        return todos.stream().map(
                todoInter -> new Todo(
                        todoInter.id(),
                        todoInter.title(),
                        todoInter.description(),
                        LocalDateTime.parse(todoInter.startDate(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        LocalDateTime.parse(todoInter.deadline(), DateTimeFormatter.ISO_LOCAL_DATE_TIME),
                        Importance.valueOf(todoInter.gradeOfImportance()),
                        transformStringToCompletionTime(todoInter.timeToComplete())
                )
                ).toList();
    }



}
