package com.m5a.autenticacion.autenticador.repository;

import java.util.Optional;

import com.m5a.autenticacion.autenticador.model.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;


@Repository
public class UserDAOImpl implements UserDAO{

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        // TODO Auto-generated method stub
        User userCreated = userRepository.save(user);
        return userCreated;
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        // TODO Auto-generated method stub

        Optional<User> user = userRepository.findByEmail(email);
        return user;
    }

    @Override
    public Boolean validateEmail(String email) {
        // TODO Auto-generated method stub
        return userRepository.existsByEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        // TODO Auto-generated method stub
        return userRepository.findById(id);
    }

    
    
}
