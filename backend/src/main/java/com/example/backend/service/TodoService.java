package com.example.backend.service;

import com.example.backend.model.TodoShort;
import com.example.backend.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository repo;
    public List<TodoShort> getAllTodos() {
        return repo.findAll();
    }
}
