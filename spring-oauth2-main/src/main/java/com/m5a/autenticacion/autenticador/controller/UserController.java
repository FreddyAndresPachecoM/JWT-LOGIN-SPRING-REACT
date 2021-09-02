package com.m5a.autenticacion.autenticador.controller;

import com.m5a.autenticacion.autenticador.exception.ResourceNotFoundException;
import com.m5a.autenticacion.autenticador.model.User;
import com.m5a.autenticacion.autenticador.security.CurrentUser;
import com.m5a.autenticacion.autenticador.security.UserData;
import com.m5a.autenticacion.autenticador.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {


    @Autowired
    private UserService userService;



    @GetMapping("/user/mysuser")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserData userPrincipal){
        return userService.findById(userPrincipal.getId())
        .orElseThrow(()-> new ResourceNotFoundException("User", "id", userPrincipal.getId()));

    }


    
}
