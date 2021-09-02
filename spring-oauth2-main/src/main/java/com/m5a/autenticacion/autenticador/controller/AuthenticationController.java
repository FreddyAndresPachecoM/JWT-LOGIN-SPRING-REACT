package com.m5a.autenticacion.autenticador.controller;

import java.net.URI;

import javax.validation.Valid;

import com.m5a.autenticacion.autenticador.exception.BadRequestException;
import com.m5a.autenticacion.autenticador.model.AuthProvider;
import com.m5a.autenticacion.autenticador.model.User;
import com.m5a.autenticacion.autenticador.payload.ApiResponse;
import com.m5a.autenticacion.autenticador.payload.AuthResponse;
import com.m5a.autenticacion.autenticador.payload.LoginRequest;
import com.m5a.autenticacion.autenticador.payload.RegisterRequest;
import com.m5a.autenticacion.autenticador.security.token.TokenProvider;
import com.m5a.autenticacion.autenticador.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {


    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenProvider tokenProvider;


    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest){

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword() )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String token = tokenProvider.createToken(authentication);

        return ResponseEntity.ok(new AuthResponse(token));

    }


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody RegisterRequest registerRequest){

        if(userService.validateEmail(registerRequest.getEmail())){
            throw new BadRequestException("Correo ya registrado");
        }


        User newUser = new User();

        newUser.setEmail(registerRequest.getEmail());
        newUser.setName(registerRequest.getName());
        newUser.setPassword(passwordEncoder.encode( registerRequest.getPassword()));
        newUser.setProvider(AuthProvider.local);

        User userCreated = userService.createUser(newUser);

        URI location = ServletUriComponentsBuilder
        .fromCurrentContextPath().path("/user/mysuser")
        .buildAndExpand(userCreated.getId()).toUri();


        return ResponseEntity.created(location)
        .body(new ApiResponse(true,"Usuario ingresado exitosamente"));


    }

    
}
