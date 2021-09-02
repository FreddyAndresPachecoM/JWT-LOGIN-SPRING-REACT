package com.m5a.autenticacion.autenticador.exception;

import org.springframework.security.core.AuthenticationException;

public class OAuth2AuthenticationProcessingException  extends AuthenticationException{

    public OAuth2AuthenticationProcessingException(String message,Throwable cause){
        super(message, cause);
    }

    public OAuth2AuthenticationProcessingException(String message){
        super(message);
    }


    
}
