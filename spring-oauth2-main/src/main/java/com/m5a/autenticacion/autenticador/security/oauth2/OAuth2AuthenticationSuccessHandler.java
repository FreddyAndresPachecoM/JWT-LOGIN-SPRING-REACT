package com.m5a.autenticacion.autenticador.security.oauth2;

import java.io.IOException;
import java.net.URI;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.m5a.autenticacion.autenticador.config.AppProperties;
import com.m5a.autenticacion.autenticador.exception.BadRequestException;
import com.m5a.autenticacion.autenticador.security.HttpCookieOAuth2AuthorizationRequestRepository;
import com.m5a.autenticacion.autenticador.security.token.TokenProvider;
import com.m5a.autenticacion.autenticador.util.CookieUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

import static com.m5a.autenticacion.autenticador.security.HttpCookieOAuth2AuthorizationRequestRepository.OAUTH2_REDIRECT_URI;

@Component
public class OAuth2AuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private TokenProvider tokenProvider;

    private AppProperties appProperties;

    private HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository;


    @Autowired
    OAuth2AuthenticationSuccessHandler(TokenProvider tokenProvider, AppProperties appProperties,
                                       HttpCookieOAuth2AuthorizationRequestRepository httpCookieOAuth2AuthorizationRequestRepository) {
        this.tokenProvider = tokenProvider;
        this.appProperties = appProperties;
        this.httpCookieOAuth2AuthorizationRequestRepository = httpCookieOAuth2AuthorizationRequestRepository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = determineTargetUrl(request, response, authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        clearAuthenticationAttributes(request, response);
        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        Optional<String> redirectUri = CookieUtils.getCookie(request, OAUTH2_REDIRECT_URI)
                .map(Cookie::getValue);

        boolean isAuthorized = isAuthorizedRedirectUri(redirectUri.get());

        if(redirectUri.isPresent() && !isAuthorized) {
            throw new BadRequestException("Sorry! We've got an Unauthorized Redirect URI and can't proceed with the authentication");
        }

        String targetUrl = redirectUri.orElse(getDefaultTargetUrl());

        String token = tokenProvider.createToken(authentication);

        return UriComponentsBuilder.fromUriString(targetUrl)
                .queryParam("token", token)
                .build().toUriString();
    }

    protected void clearAuthenticationAttributes(HttpServletRequest request, HttpServletResponse response) {
        super.clearAuthenticationAttributes(request);
        httpCookieOAuth2AuthorizationRequestRepository.removeAuthorizationCookies(request, response);
    }

    private boolean isAuthorizedRedirectUri(String uri) {
        

        return appProperties.getOauth2().getAuthorizedRedirectUris()
                .stream()
                .anyMatch(authorizedRedirectUri -> {
                    // Only validate host and port. Let the clients use different paths if they want to
                    URI clientRedirectUri = URI.create(uri);
                    String clientHost = clientRedirectUri.getHost();
                    int clientPort = clientRedirectUri.getPort();
                    
                    URI authorizedURI = URI.create(authorizedRedirectUri);
                    String authorizedHost = authorizedURI.getHost();
                    int authorizedPort = authorizedURI.getPort();
                    if(authorizedHost.equalsIgnoreCase(clientHost)) {
                        if( authorizedURI.getPort() == clientRedirectUri.getPort())
                            return true;
                        else
                            return false;
                    }
                    return false;
                });
    }
}
