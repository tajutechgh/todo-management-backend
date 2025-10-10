package com.tajutechgh.todobackend.mapper;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;

public class TodoMapper {

    public static TodoDto mapToTodoDto(Todo todo) {
        return new TodoDto(
                todo.getId(),
                todo.getTitle(),
                todo.getDescription(),
                todo.isCompleted(),
                todo.getUser().getId()
        );
    }

    public static Todo mapToTodo(TodoDto todoDto) {

        Todo todo = new Todo();

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setCompleted(todoDto.isCompleted());
        todo.setUser(todo.getUser());

        return todo;
    }
}
