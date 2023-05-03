package com.pebblepost.todo;

import javassist.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class TodoServiceTest {

//    @InjectMocks
//    private TodoService todoService;
//
//    @Mock
//    private TodoRepository todoRepo;

    @Mock
    private TodoRepository todoRepo;

    private TodoService todoService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        todoService = new TodoService(todoRepo);
    }
    @Test
    void createTodo() {
        Todo todo = new Todo();
        todo.setTitle("Test Todo");
        when(todoRepo.save(todo)).thenReturn(todo);
        Todo createdTodo = todoService.createTodo(todo);
        assertEquals("Test Todo", createdTodo.getTitle());
        verify(todoRepo, times(1)).save(todo);
    }

    @Test
    void getTodos() {
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
        when(todoRepo.findAll()).thenReturn(todos);

        //when
        List<Todo> ret = todoService.getTodos();
        assertEquals(2, ret.size());
        assertEquals(1L, ret.get(0).getId().longValue());
        assertEquals("title1", ret.get(0).getTitle());
        assertFalse(ret.get(0).getCompleted());
        verify(todoRepo, times(1)).findAll();
    }

    @Test
    void getTodo() throws NotFoundException {
        //assume given
        Todo todo1 = new Todo();
        Long id =1L;
        todo1.setId(id);
        todo1.setTitle("title1");
        todo1.setCompleted(false);
        when(todoRepo.findById(id)).thenReturn(Optional.of(todo1));

        //call
        Todo ret = todoService.getTodo(id);

        assertEquals(1L, ret.getId().longValue());
        assertEquals("title1", ret.getTitle());
        assertFalse(ret.getCompleted());
        verify(todoRepo, times(1)).findById(id);
    }

    @Test
    void updateTodo() throws NotFoundException {
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        Optional<Todo> optionalTodo = Optional.of(todo);
        when(todoRepo.findById(1L)).thenReturn(optionalTodo);
        when(todoRepo.save(todo)).thenReturn(todo);
        Todo updatedTodo = new Todo();
        updatedTodo.setTitle("Updated Todo");
        Todo returnedTodo = todoService.updateTodo(1L, updatedTodo);
        assertEquals("Updated Todo", returnedTodo.getTitle());
        verify(todoRepo, times(1)).findById(1L);
        verify(todoRepo, times(1)).save(todo);
    }

    @Test
    void deleteTodo() throws NotFoundException {

        Long id = 1L;

        Todo todo = new Todo();
        todo.setId(id);
        todo.setTitle("Test Todo");

     
        when(todoRepo.findById(any(Long.class))).thenReturn(Optional.of(todo));
        doNothing().when(todoRepo).delete(any(Todo.class));

        //call
        todoService.deleteTodo(id);
        verify(todoRepo,atLeastOnce()).delete(any(Todo.class));
    }
}
