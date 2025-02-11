package com.tajutechgh.todobackend.controller;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;
import com.tajutechgh.todobackend.service.TodoService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
@Validated
@RequestMapping("/api/v1/todos")
public class TodoController {

    private ModelMapper modelMapper;

    private TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    // TOD0: create todo
    @PostMapping("/create")
    public ResponseEntity<TodoDto> createTodo(@RequestBody TodoDto todoDto) {

        TodoDto savedTodoDto = todoService.createTodo(todoDto);

        return new ResponseEntity<>(savedTodoDto, HttpStatus.CREATED);
    }

    // TODO: get all todos
    @GetMapping("/all")
    public ResponseEntity<List<TodoDto>> getAllTodos() {

        List<TodoDto> todoDtos = todoService.getAllTodos();

        return new ResponseEntity<>(todoDtos, HttpStatus.OK);
    }

    @GetMapping("/all-todos")
    public  ResponseEntity<List<TodoDto>> getAllTodosByPage(
            @RequestParam(value = "page", required = false, defaultValue = "1") @Min(value = 1) Integer pageNum,
            @RequestParam(value = "size", required = false, defaultValue = "5") @Min(value = 5) @Max(value = 10) Integer pageSize,
            @RequestParam(value = "sort", required = false, defaultValue = "id") String sortOption ){

        Page<TodoDto> page = todoService.listByPage(pageNum - 1, pageSize, sortOption);

        List<TodoDto> todoDtos = page.getContent();

        if (todoDtos.isEmpty()){

            return ResponseEntity.noContent().build();
        }

        return new ResponseEntity<>(todoDtos, HttpStatus.OK);
    }

    // TODO: get a todo
    @GetMapping("/get/{id}")
    public ResponseEntity<TodoDto> getTodo( @PathVariable(value = "id") Long id) {

        TodoDto todoDto = todoService.getTodo(id);

        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // TODO: update a todo
    @PutMapping("/update/{id}")
    public ResponseEntity<TodoDto> updateTodo(@PathVariable(value = "id") Long id, @RequestBody TodoDto todoDto) {

        TodoDto updatedTodoDto = todoService.updateTodo(id, todoDto);

        return new ResponseEntity<>(updatedTodoDto, HttpStatus.OK);
    }

    // TODO: delete a todo
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteTodo(@PathVariable(value = "id") Long id) {

        todoService.deleteTodo(id);

        return new ResponseEntity<>("Todo deleted successfully", HttpStatus.OK);
    }

    // TODO: complete a todo
    @PatchMapping("/complete/{id}")
    public ResponseEntity<TodoDto> completeTodo(@PathVariable(value = "id") Long id) {

        TodoDto todoDto = todoService.completeTodo(id);

        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }

    // TODO: inComplete a todo
    @PatchMapping("/incomplete/{id}")
    public ResponseEntity<TodoDto> inCompleteTodo(@PathVariable(value = "id") Long id) {

        TodoDto todoDto = todoService.inCompleteTodo(id);

        return new ResponseEntity<>(todoDto, HttpStatus.OK);
    }
}
