package com.pebblepost.todo;

import javassist.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController()
@RequestMapping("/todos")
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TodoDto create(@RequestBody TodoDto createDto) {
   
        Todo todo = getTodoFromDTO(createDto);
        TodoDto returned= getDTOFromTodo(todoService.createTodo(todo));
        return returned;
    }

    @GetMapping
    public List<TodoDto> getAll() {
       
        List<Todo> l = todoService.getTodos();
        List<TodoDto> res = new ArrayList<TodoDto>();
        for(Todo t : l){
            res.add(getDTOFromTodo(t));
        }
        return res;

    }

    @GetMapping("/{id}")
    public TodoDto getOne(@PathVariable("id") Long id) throws NotFoundException {
       

            return getDTOFromTodo(todoService.getTodo(id));


    }

    @PutMapping("/{id}")
    @ResponseStatus(value = HttpStatus.OK)
    public TodoDto put(@PathVariable("id") Long id, @RequestBody TodoDto updated) throws NotFoundException {
       
       return getDTOFromTodo(todoService.updateTodo(id,getTodoFromDTO(updated)));
    }

    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable("id") Long id) throws NotFoundException{
    
        todoService.deleteTodo(id);

    }

    
        private TodoDto getDTOFromTodo(Todo todo) {
        TodoDto todoDTO = new TodoDto();
        todoDTO.setId(todo.getId());
        todoDTO.setTitle(todo.getTitle());
        todoDTO.setCompleted(todo.getCompleted());
        return todoDTO;

            //return TodoDto.fromEntity(todo);

    }

    private Todo getTodoFromDTO(TodoDto todoDTO) {
        Todo todo = new Todo();
        todo.setId(todoDTO.getId());
        todo.setTitle(todoDTO.getTitle());
        todo.setCompleted(todoDTO.getCompleted());
        return todo;
        //return TodoDto.toEntity(todoDTO);
    }

}
