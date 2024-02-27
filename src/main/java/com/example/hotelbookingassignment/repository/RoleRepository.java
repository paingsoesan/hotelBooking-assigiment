package com.example.hotelbookingassignment.repository;

import com.example.hotelbookingassignment.ds.Role;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoleRepository extends CrudRepository<Role, Integer> {
    Optional<Role> findRoleByRoleName(String roleName);
}
