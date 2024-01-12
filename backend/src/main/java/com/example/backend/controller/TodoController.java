package com.example.backend.controller;

import com.example.backend.model.Todo;
import com.example.backend.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/todo/")
@RequiredArgsConstructor
public class TodoController {

 private final TodoService service;

 @GetMapping
 public List<Todo> getAllTodos(){
     return service.getAllTodos();
 }

}
