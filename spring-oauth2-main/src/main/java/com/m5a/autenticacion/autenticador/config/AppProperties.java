package com.m5a.autenticacion.autenticador.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "app")
public class AppProperties {

    private final Auth auth = new Auth();
    private final OAuth2 oauth2 = new OAuth2();

    public static class Auth {
        private String tokenSecret;
        private long tokenExpirationMsec;

        /**
         * @return String return the tokenSecret
         */
        public String getTokenSecret() {
            return tokenSecret;
        }

        /**
         * @param tokenSecret the tokenSecret to set
         */
        public void setTokenSecret(String tokenSecret) {
            this.tokenSecret = tokenSecret;
        }

        /**
         * @return long return the tokenExpirationMsec
         */
        public long getTokenExpirationMsec() {
            return tokenExpirationMsec;
        }

        /**
         * @param tokenExpirationMsec the tokenExpirationMsec to set
         */
        public void setTokenExpirationMsec(long tokenExpirationMsec) {
            this.tokenExpirationMsec = tokenExpirationMsec;
        }

    }

    public static final class OAuth2 {

        private List<String> authorizedRedirectUris = new ArrayList<>();

        public List<String> getAuthorizedRedirectUris() {
            return authorizedRedirectUris;
        }

        public OAuth2 authorizedRedirectUris(List<String> authorizedRedirectUris) {
            this.authorizedRedirectUris = authorizedRedirectUris;
            return this;
        }

    }

    public Auth getAuth() {
        return auth;
    }

    public OAuth2 getOauth2() {
        return oauth2;
    }
}
