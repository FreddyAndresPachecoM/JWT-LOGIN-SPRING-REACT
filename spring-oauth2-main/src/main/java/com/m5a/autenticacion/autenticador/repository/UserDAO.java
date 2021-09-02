package com.m5a.autenticacion.autenticador.repository;

import java.util.Optional;

import com.m5a.autenticacion.autenticador.model.User;

public interface UserDAO {

    User createUser(User user);

    Optional<User> findUserByEmail(String email);

    Boolean validateEmail(String email);

    Optional<User> findById(Long id);
    
}
