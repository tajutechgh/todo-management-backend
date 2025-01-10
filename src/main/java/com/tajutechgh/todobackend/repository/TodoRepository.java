package com.tajutechgh.todobackend.repository;

import com.tajutechgh.todobackend.entity.Todo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TodoRepository extends JpaRepository<Todo, Long> {

}