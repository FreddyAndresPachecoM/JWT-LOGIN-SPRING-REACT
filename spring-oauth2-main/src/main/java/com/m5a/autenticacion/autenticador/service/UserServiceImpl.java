package com.m5a.autenticacion.autenticador.service;

import java.util.Optional;

import com.m5a.autenticacion.autenticador.model.User;
import com.m5a.autenticacion.autenticador.repository.UserDAO;
import com.m5a.autenticacion.autenticador.security.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDAO userDAO;

    @Override
    public User createUser(User user) {
        // TODO Auto-generated method stub

        if (!userDAO.validateEmail(user.getEmail())) {
            return userDAO.createUser(user);
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        // TODO Auto-generated method stub
        return userDAO.findUserByEmail(email).orElse(null);
    }

    @Override
    public Boolean validateEmail(String email) {
        // TODO Auto-generated method stub
        return userDAO.validateEmail(email);
    }

    @Override
    public Optional<User> findById(Long id) {
        // TODO Auto-generated method stub
        return userDAO.findById(id);
    }

}
