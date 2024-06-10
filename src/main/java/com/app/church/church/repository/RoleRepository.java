package com.app.church.church.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.CrudRepository;

import com.app.church.church.entities.users.Role;

import jakarta.persistence.LockModeType;



public interface RoleRepository extends CrudRepository<Role, Long> {

    Optional<Role> findByRole(String role);

    @SuppressWarnings("null")
    @Lock(LockModeType.READ)
    public List<Role> findAll();

}
