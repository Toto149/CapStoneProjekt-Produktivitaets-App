package com.example.backend;


import com.example.backend.model.TodoShortDB;
import com.example.backend.repository.TodoRepository;
import com.example.backend.service.IdService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest
@AutoConfigureMockMvc
 class TodoIntegrationTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private TodoRepository repo;
    @Autowired
    IdService service;

    @DirtiesContext
    @Test
    void getAllTodos_shouldReturnListWithOneTodo_whenOneTodoWasSavedInRepo() throws Exception{
        //GIVEN
        TodoShortDB todo = new TodoShortDB(
                "1",
                "Test",
                "Test",
                "2023-12-12T12:12:12",
                "2023-12-12T12:12:12"
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
                            "deadline" : "2023-12-12T12:12:12"
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
    void addTodo_shouldReturnCreatedTodo() throws Exception {

        when(mock(service.getClass()).generateId()).thenReturn("1");
        mvc.perform(MockMvcRequestBuilders.put("/api/todo/save/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        """
                    {
                    "title": "Test",
                    "description" : "Test",
                    "startDate" : "2023-12-12T12:12:12",
                    "deadline": "2023-12-12T12:12:12"
                    }
                    """
                ))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().json(
                        """
                        
                                {
                            "id": "1",
                            "title" : "Test",
                            "description" : "Test",
                            "startDate": "2023-12-12T12:12:12",
                            "deadline" : "2023-12-12T12:12:12"
                        }
                        """
                ));
    }

}
