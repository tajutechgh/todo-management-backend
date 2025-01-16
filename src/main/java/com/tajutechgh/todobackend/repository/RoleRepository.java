package com.tajutechgh.todobackend.repository;

import com.tajutechgh.todobackend.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long> {

     Role findByName(String name);
}
