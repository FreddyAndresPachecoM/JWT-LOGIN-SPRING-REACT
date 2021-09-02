package com.m5a.autenticacion.autenticador.payload;

public class AuthResponse {

    private String accessToken;
    private String tokenType = "Bearer";


    public AuthResponse(String accessToken){
        this.accessToken=accessToken;
    }

    
    

    /**
     * @return String return the accessToken
     */
    public String getAccessToken() {
        return accessToken;
    }

    /**
     * @param accessToken the accessToken to set
     */
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    /**
     * @return String return the tokenType
     */
    public String getTokenType() {
        return tokenType;
    }

    /**
     * @param tokenType the tokenType to set
     */
    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

}
