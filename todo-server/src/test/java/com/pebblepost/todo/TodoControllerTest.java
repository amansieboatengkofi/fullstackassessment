package com.pebblepost.todo;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import javassist.NotFoundException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@SpringBootTest
@AutoConfigureMockMvc
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @InjectMocks
    private TodoController todoController;

    @Mock
    private TodoService todoService;

    @Test
    void create()  {
        TodoDto createDto = new TodoDto();
        createDto.setTitle("title");
        createDto.setCompleted(false);
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("title");
        todo.setCompleted(false);
        when(todoService.createTodo(any(Todo.class))).thenReturn(todo);

        //when
        TodoDto returned = todoController.create(createDto);

        //then
        assertEquals(1L, returned.getId().longValue());
        assertEquals("title", returned.getTitle());
        assertFalse(returned.getCompleted());
//        mockMvc.perform(post("/todos")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(todoDTO)))
//                .andExpect(status().isOk());
    }

    @Test
    void getAll() {

        Todo todo1 = new Todo();
        todo1.setId(1L);
        todo1.setTitle("title1");
        todo1.setCompleted(false);
        Todo todo2 = new Todo();
        todo2.setId(2L);
        todo2.setTitle("title2");
        todo2.setCompleted(false);
        List<Todo> todos = new ArrayList<>();
        todos.add(todo1);
        todos.add(todo2);
        when(todoService.getTodos()).thenReturn(todos);

        //when
        List<TodoDto> ret = todoController.getAll();
        assertEquals(2, ret.size());
        assertEquals(1L, ret.get(0).getId().longValue());
        assertEquals("title1", ret.get(0).getTitle());
        assertFalse(ret.get(0).getCompleted());


    }

    @Test
    void getOne() throws NotFoundException {
        //assume given
        Todo todo1 = new Todo();
        Long id =1L;
        todo1.setId(id);
        todo1.setTitle("title1");
        todo1.setCompleted(false);
        when(todoService.getTodo(id)).thenReturn(todo1);

        //call
        TodoDto ret = todoController.getOne(id);

        assertEquals(1L, ret.getId().longValue());
        assertEquals("title1", ret.getTitle());
        assertFalse(ret.getCompleted());

    }

    @Test
    void put() throws NotFoundException {
        Long todoId = 1L;
        TodoDto updateDto = new TodoDto();
        updateDto.setTitle("Test Title");
        updateDto.setCompleted(true);
        Todo todo = new Todo();
        todo.setId(todoId);
        todo.setTitle("Test Title");
        todo.setCompleted(true);
        when(todoService.updateTodo(eq(todoId), any(Todo.class))).thenReturn(todo);

        //when
        TodoDto returned = todoController.put(todoId, updateDto);

        //then
        assertEquals(1L, returned.getId().longValue());
        assertEquals("Test Title", returned.getTitle());
        assertTrue(returned.getCompleted());

    }

    @Test
    void delete() throws NotFoundException {
        Long id = 1L;
        doNothing().when(todoService).deleteTodo(id);
        todoController.delete(id);
        verify(todoService,atLeastOnce()).deleteTodo(id);


    }

}
