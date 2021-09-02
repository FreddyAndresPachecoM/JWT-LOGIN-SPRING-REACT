package com.m5a.autenticacion.autenticador.security.oauth2;

import java.util.Map;

import com.m5a.autenticacion.autenticador.exception.OAuth2AuthenticationProcessingException;
import com.m5a.autenticacion.autenticador.model.AuthProvider;

public class OAuth2UserInfoCustom {

    public static OAuth2UserInfo gOAuth2UserInfo(String registrationId,Map<String,Object> attributes){
        if(registrationId.equalsIgnoreCase(AuthProvider.google.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        }
        else if(registrationId.equalsIgnoreCase(AuthProvider.facebook.toString())){
            return new GoogleOAuth2UserInfo(attributes);
        }
        else{
            throw new OAuth2AuthenticationProcessingException("Lo sentimos, el inicio de sesión de "+registrationId+" no está permitido");
        }
    }
    
}
