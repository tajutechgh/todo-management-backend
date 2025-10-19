package com.tajutechgh.todobackend.service;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TodoService {

    TodoDto createTodo(TodoDto todoDto, Integer userId);
    TodoDto getTodo(Integer id);
    List<TodoDto> getAllTodos();
    List<TodoDto> getAllCompletedTodosByUser(boolean completed, Integer userId);
    List<TodoDto> getAllPendingTodosByUser(boolean completed, Integer userId);
    Page<TodoDto> listByPage(int pageNum, int pageSize, String sortField);
    TodoDto updateTodo(Integer id, TodoDto todoDto);
    void deleteTodo(Integer id);
    TodoDto completeTodo(Integer id);
    TodoDto inCompleteTodo(Integer id);
}
