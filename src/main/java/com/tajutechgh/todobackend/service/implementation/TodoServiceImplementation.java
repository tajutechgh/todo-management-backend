package com.tajutechgh.todobackend.service.implementation;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;
import com.tajutechgh.todobackend.exception.ResourceNotFoundException;
import com.tajutechgh.todobackend.mapper.TodoMapper;
import com.tajutechgh.todobackend.repository.TodoRepository;
import com.tajutechgh.todobackend.service.TodoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImplementation implements TodoService {

    private TodoRepository todoRepository;

    public TodoServiceImplementation(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto) {

        Todo todo = TodoMapper.mapToTodo(todoDto);

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public TodoDto getTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        return TodoMapper.mapToTodoDto(todo);
    }

    @Override
    public List<TodoDto> getAllTodos() {

        List<Todo> todos = todoRepository.findAll();

        return todos.stream().map(TodoMapper::mapToTodoDto).collect(Collectors.toList());
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public void deleteTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todo.setCompleted(true);

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public TodoDto inCompleteTodo(Long id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todo.setCompleted(false);

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }
}
