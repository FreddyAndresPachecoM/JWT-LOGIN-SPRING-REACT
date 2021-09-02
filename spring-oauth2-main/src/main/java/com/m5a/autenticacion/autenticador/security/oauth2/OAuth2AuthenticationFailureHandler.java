package com.m5a.autenticacion.autenticador.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static com.m5a.autenticacion.autenticador.security.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_REDIRECT_URI;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.m5a.autenticacion.autenticador.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.m5a.autenticacion.autenticador.util.CookieUtils;


@Component
public class OAuth2AuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler{

    @Autowired
    HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest,HttpServletResponse httpServletResponse, AuthenticationException authenticationException)
    throws IOException,ServletException
    {

        String targetUrl = CookieUtils.getCookie(httpServletRequest, OAUTH2_REDIRECT_URI)
        .map(Cookie::getValue)
        .orElse(("/"));

        targetUrl = UriComponentsBuilder.fromUriString(targetUrl)
        .queryParam("error", authenticationException.getLocalizedMessage())
        .build().toUriString();

        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(httpServletRequest, httpServletResponse);

        getRedirectStrategy().sendRedirect(httpServletRequest, httpServletResponse, targetUrl);

        

    }

    
}
