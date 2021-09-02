package com.m5a.autenticacion.autenticador.security;

import java.util.Optional;


import com.m5a.autenticacion.autenticador.exception.BadRequestException;
import com.m5a.autenticacion.autenticador.exception.OAuth2AuthenticationProcessingException;
import com.m5a.autenticacion.autenticador.model.AuthProvider;
import com.m5a.autenticacion.autenticador.model.User;
import com.m5a.autenticacion.autenticador.repository.UserRepository;
import com.m5a.autenticacion.autenticador.security.oauth2.OAuth2UserInfo;
import com.m5a.autenticacion.autenticador.security.oauth2.OAuth2UserInfoCustom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.stereotype.Service;

import org.springframework.security.core.AuthenticationException;

@Service
public class CustomOAuth2UserService extends DefaultOAuth2UserService {

    @Autowired
    private UserRepository userRepository;

    protected static String type="";

    @Override
    public OAuth2User loadUser(OAuth2UserRequest oAuth2UserRequest) throws OAuth2AuthenticationException {
        OAuth2User oAuth2User = super.loadUser(oAuth2UserRequest);

        try {
            return processOAuth2User(oAuth2UserRequest, oAuth2User);
        } catch (AuthenticationException ex) {
            throw ex;
        } catch (Exception ex) {
            // Throwing an instance of AuthenticationException will trigger the
            // OAuth2AuthenticationFailureHandler
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex.getCause());
        }
    }

    public OAuth2User processOAuth2User(OAuth2UserRequest oAuth2UserRequest, OAuth2User oAuth2User) {
        System.out.println(type);
        OAuth2UserInfo oAuth2UserInfo = OAuth2UserInfoCustom.gOAuth2UserInfo(
                oAuth2UserRequest.getClientRegistration().getRegistrationId(), oAuth2User.getAttributes());

        if (oAuth2UserInfo.getEmail().isEmpty()) {
            throw new OAuth2AuthenticationProcessingException("No se pudo encontrar el email");
        }

        Optional<User> userIntern = userRepository.findByEmail(oAuth2UserInfo.getEmail());
        User user;

        if (userIntern.isPresent()) {
            user = userIntern.get();

            if (type.equals("register")) {

                throw new BadRequestException("Cuenta ya registrada");
            }else{

                if (!user.getProvider()
                        .equals(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()))) {

                    throw new OAuth2AuthenticationProcessingException("Usted está intentando iniciar sesión con "
                            + user.getProvider() + ". Por favor inicie sesión con la cuenta de " + user.getProvider());
                }
            }
            // user = updateUser(user, oAuth2UserInfo);
        } else {
            if (type.equals("login")) {
                throw new BadRequestException("Usuario no registrado");
            } else {
                user = registerNewUser(oAuth2UserRequest, oAuth2UserInfo);
            }

        }

        return UserData.create(user);
    }

    private User registerNewUser(OAuth2UserRequest oAuth2UserRequest, OAuth2UserInfo oAuth2UserInfo) {

        User user = new User();
        user.setProvider(AuthProvider.valueOf(oAuth2UserRequest.getClientRegistration().getRegistrationId()));
        user.setProviderId(oAuth2UserInfo.getId());
        user.setName(oAuth2UserInfo.getName());
        user.setEmail(oAuth2UserInfo.getEmail());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

    private User updateUser(User user, OAuth2UserInfo oAuth2UserInfo) {
        user.setName(oAuth2UserInfo.getName());
        user.setImageUrl(oAuth2UserInfo.getImageUrl());
        return userRepository.save(user);
    }

}
