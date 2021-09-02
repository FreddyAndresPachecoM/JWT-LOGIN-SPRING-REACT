package com.m5a.autenticacion.autenticador.service;

import com.m5a.autenticacion.autenticador.model.User;
import com.m5a.autenticacion.autenticador.repository.UserDAO;
import com.m5a.autenticacion.autenticador.security.UserData;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    @Autowired
    UserDAO userDAO;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // TODO Auto-generated method stub

        User user = userDAO.findUserByEmail(email)
        .orElseThrow(() -> new UsernameNotFoundException("No pudimos encontrar el usuario con email: "+email));
        return UserData.create(user);
    }

    public UserDetails loadUserById(Long id){
        // TODO Auto-generated method stub

        User user = userDAO.findById(id)
        .orElseThrow(() -> new UsernameNotFoundException("No pudimos encontrar el usuario con el id: "+id));
        return UserData.create(user);
    }


    
}
