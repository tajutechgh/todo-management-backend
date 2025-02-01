package com.tajutechgh.todobackend.service;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TodoService {

    TodoDto createTodo(TodoDto todoDto);

    TodoDto getTodo(Long id);

    List<TodoDto> getAllTodos();

    Page<TodoDto> listByPage(int pageNum, int pageSize, String sortField);

    TodoDto updateTodo(Long id, TodoDto todoDto);

    void deleteTodo(Long id);

    TodoDto completeTodo(Long id);

    TodoDto inCompleteTodo(Long id);
}
