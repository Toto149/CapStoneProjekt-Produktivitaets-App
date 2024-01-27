package com.example.backend.controller;

import com.example.backend.model.PostTodo;
import com.example.backend.model.TodoShort;
import com.example.backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping("/todo/get")
    public List<TodoShort> getAllTodos(){
     return service.getAllTodos();
 }

    @PostMapping("/todo/save")
    public TodoShort saveTodo(@RequestBody PostTodo todoToSave){
        return service.saveTodo(todoToSave);
    }

    @DeleteMapping("/todo/delete/{id}")
    public boolean deleteTodoWithId(@PathVariable String id){
        return service.deleteTodo(id);
    }

    @PutMapping("/todo/save/{id}")
    public TodoShort updateTodo(@PathVariable String id, @RequestBody PostTodo todoToUpdate){
        return service.updateTodo(id, todoToUpdate);
    }
}
