package com.tajutechgh.todobackend.service.implementation;

import com.tajutechgh.todobackend.dto.TodoDto;
import com.tajutechgh.todobackend.entity.Todo;
import com.tajutechgh.todobackend.entity.User;
import com.tajutechgh.todobackend.exception.ResourceNotFoundException;
import com.tajutechgh.todobackend.mapper.TodoMapper;
import com.tajutechgh.todobackend.repository.TodoRepository;
import com.tajutechgh.todobackend.repository.UserRepository;
import com.tajutechgh.todobackend.service.TodoService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TodoServiceImplementation implements TodoService {

    private TodoRepository todoRepository;
    private UserRepository userRepository;

    public TodoServiceImplementation(TodoRepository todoRepository, UserRepository userRepository) {
        this.todoRepository = todoRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TodoDto createTodo(TodoDto todoDto, Integer userId) {

        User user = userRepository.findById(userId).orElseThrow(

                () -> new ResourceNotFoundException("User ", "id",  userId )
        );

        Todo todo = new Todo();

        todo.setTitle(todoDto.getTitle());
        todo.setDescription(todoDto.getDescription());
        todo.setUser(user);

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public TodoDto getTodo(Integer id) {

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
    public List<TodoDto> getAllCompletedTodosByUser(boolean completed, Integer userId) {

        List<Todo> completedTodos = todoRepository.findAllTodosByCompletedByUserId(completed, userId);

        return completedTodos.stream().map(TodoMapper::mapToTodoDto).collect(Collectors.toList());
    }

    @Override
    public List<TodoDto> getAllPendingTodosByUser(boolean completed, Integer userId) {

        List<Todo> pendingTodos = todoRepository.findAllTodosByCompletedByUserId(completed, userId);

        return pendingTodos.stream().map(TodoMapper::mapToTodoDto).collect(Collectors.toList());
    }

    @Override
    public Page<TodoDto> listByPage(int pageNum, int pageSize, String sortField) {

        Sort sort = Sort.by(sortField).ascending();

        Pageable pageable = PageRequest.of(pageNum, pageSize, sort);

        return todoRepository.findAll(pageable).map(TodoMapper::mapToTodoDto);
    }

    @Override
    public TodoDto updateTodo(Integer id, TodoDto todoDto) {

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
    public void deleteTodo(Integer id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todoRepository.delete(todo);
    }

    @Override
    public TodoDto completeTodo(Integer id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todo.setCompleted(true);

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }

    @Override
    public TodoDto inCompleteTodo(Integer id) {

        Todo todo = todoRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Todo", "id", id)
        );

        todo.setCompleted(false);

        Todo savedTodo = todoRepository.save(todo);

        return TodoMapper.mapToTodoDto(savedTodo);
    }
}
