package com.example.backend.controller;

import com.example.backend.model.PostTodo;
import com.example.backend.model.Todo;
import com.example.backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todo")
@RequiredArgsConstructor
public class TodoController {

    private final TodoService service;

    @GetMapping("/get")
    public List<Todo> getAllTodos(){
     return service.getAllTodos();
 }


    @PostMapping("/save")
    public Todo saveTodo(@RequestBody PostTodo todoToSave){
        return service.saveTodo(todoToSave);
    }

    @DeleteMapping("/delete/{id}")
    public boolean deleteTodoWithId(@PathVariable String id){
        return service.deleteTodo(id);
    }

    @PutMapping("/save/{id}")
    public Todo updateTodo(@PathVariable String id, @RequestBody PostTodo todoToUpdate){
        return service.updateTodo(id, todoToUpdate);
    }
}
