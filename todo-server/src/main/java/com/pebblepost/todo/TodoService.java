package com.pebblepost.todo;

import javassist.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    Todo createTodo(Todo newTodo) {
        return todoRepository.save(newTodo);
      

    }

    public List<Todo> getTodos() {

        
        List<Todo> l = todoRepository.findAll();
        return l;
    }

    public Todo getTodo(Long id) throws NotFoundException {
      
        Todo t = todoRepository.findById(id).orElseThrow(()->new NotFoundException("TODO not found for id: "+ id ));
        return t;

    }

    public Todo updateTodo(Long id, Todo updatedTodo) throws NotFoundException{

   
        Todo t = getTodo(id);
        t.setTitle(updatedTodo.getTitle());
        t.setCompleted(updatedTodo.getCompleted());
        return todoRepository.save(t);

    }

    public void deleteTodo(Long id) throws NotFoundException {
       
        Todo t = getTodo(id);
        todoRepository.delete(t);
    }

}
