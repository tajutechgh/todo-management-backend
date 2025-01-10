package com.tajutechgh.todobackend.mapper;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;

public class TodoMapper {

    public static TodoDto mapToTodoDto(Todo todo) {
        return new TodoDto(todo.getId(), todo.getTitle(), todo.getDescription(), todo.isCompleted());
    }

    public static Todo mapToTodo(TodoDto todoDto) {
        return new Todo(todoDto.getId(), todoDto.getTitle(), todoDto.getDescription(), todoDto.isCompleted());
    }
}
