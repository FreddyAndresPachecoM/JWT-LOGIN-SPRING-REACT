package com.m5a.autenticacion.autenticador.service;

import java.util.Optional;

import javax.print.DocFlavor.STRING;

import com.m5a.autenticacion.autenticador.model.User;

public interface UserService {

    User createUser(User user);

    User findUserByEmail(String email);

    Boolean validateEmail(String email);

    Optional<User> findById(Long id);

}
