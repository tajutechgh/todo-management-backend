package com.tajutechgh.todobackend.repository;

import com.tajutechgh.todobackend.entity.Todo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TodoRepository extends JpaRepository<Todo, Integer> {

    Page<Todo> findAll(Pageable pageable);

    @Query("SELECT t FROM Todo t WHERE t.completed = ?1 AND t.user.id = ?2")
    List<Todo> findAllByCompleted(boolean completed, Integer userId);
}