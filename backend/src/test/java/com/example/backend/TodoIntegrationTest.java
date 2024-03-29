package com.example.backend;

import com.example.backend.model.TodoDB;
import com.example.backend.repository.TodoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
 class TodoIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoRepository repo;
    @DirtiesContext
    @Test
    void getAllTodos_shouldReturnListWithOneTodo_whenOneTodoWasSavedInRepo() throws Exception {
        //GIVEN
        TodoDB todo = new TodoDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        );
        repo.save(todo);
        //WHEN + THEN
        mvc.perform(MockMvcRequestBuilders.get("/api/todo/get"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                                [
                            {
                            "id" : "1",
                            "title" : "Test",
                            "description" : "Test",
                            "startDate" :  "2023-12-12T12:12:12",
                            "deadline" : "2023-12-12T12:12:12",
                            "gradeOfImportance": "BARELY_IMPORTANT",
                            "timeToComplete": {
                            "amount":  100,
                            "timeUnit":  "HOURS"
                            }
                            }
                        ]
                        """
                ));
    }
    @DirtiesContext
    @Test
    void getAllTodos_shouldReturnEmptyList_whenNoTodoIsSaved() throws Exception {
        mvc.perform(MockMvcRequestBuilders.get("/api/todo/get"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"
                ));
    }

    @DirtiesContext
    @Test
    void deleteTodo_shouldReturnEmptyList_whenDeletingOnlyElement() throws Exception {
        //GIVEN
        TodoDB todo = new TodoDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12",
                "BARELY_IMPORTANT",
                "100 hours"
        );
        repo.save(todo);
        mvc.perform(MockMvcRequestBuilders.delete("/api/todo/delete/1"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mvc.perform(MockMvcRequestBuilders.get("/api/todo/get"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json("[]"));
}
}