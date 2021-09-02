package com.m5a.autenticacion.autenticador.repository;

import java.util.Optional;

import com.m5a.autenticacion.autenticador.model.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByEmail(String email);
    Boolean existsByEmail(String email);
    
}
