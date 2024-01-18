package com.example.backend.repository;

import com.example.backend.model.TodoShortDB;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TodoRepository extends MongoRepository<TodoShortDB, String> {

}
