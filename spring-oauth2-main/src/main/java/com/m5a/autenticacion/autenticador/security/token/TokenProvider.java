package com.m5a.autenticacion.autenticador.security.token;

import java.util.Date;

import com.m5a.autenticacion.autenticador.config.AppProperties;
import com.m5a.autenticacion.autenticador.security.UserData;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class TokenProvider {

    private static final Logger logger = LoggerFactory.getLogger(TokenProvider.class);

    private AppProperties appProperties;
    public TokenProvider(AppProperties appProperties){

        this.appProperties = appProperties;

    }

    public String createToken(Authentication authentication){

        UserData userData = (UserData)authentication.getPrincipal();

        Date now = new Date();

        Date expireDate = new Date(now.getTime()+appProperties.getAuth().getTokenExpirationMsec());

        return Jwts.builder()
        .setSubject(Long.toString(userData.getId()))
        .setIssuedAt(new Date())
        .setExpiration(expireDate)
        .signWith(SignatureAlgorithm.HS512, appProperties.getAuth().getTokenSecret())
        .compact();



    }


    public boolean validateToken(String authToken){
        try{
            Jwts.parser().setSigningKey(appProperties.getAuth().getTokenSecret()).parseClaimsJws(authToken);
            return true;
        }
        catch(SignatureException ex){

            logger.error("Firma de JWT inválida");
        }
        catch(MalformedJwtException ex){
            logger.error("TOken inválido");
        }
        catch(UnsupportedJwtException ex){
            logger.error("Token JWT no soportado");
        }
        catch(ExpiredJwtException ex){
            logger.error("Token expirado");
        }

        return false;
    }

    public Long getUserIdToken(String token){

        Claims claims = Jwts.parser()
        .setSigningKey(appProperties.getAuth().getTokenSecret())
        .parseClaimsJws(token)
        .getBody();

        return Long.parseLong(claims.getSubject());
    }
    
    
}
