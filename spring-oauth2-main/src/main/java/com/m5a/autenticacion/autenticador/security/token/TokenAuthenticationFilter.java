package com.m5a.autenticacion.autenticador.security.token;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.m5a.autenticacion.autenticador.service.CustomUserDetailsService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

public class TokenAuthenticationFilter extends OncePerRequestFilter{


    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    public final String PREFIX = "Bearer ";

    private static final Logger logger = LoggerFactory.getLogger(TokenAuthenticationFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // TODO Auto-generated method stub

        try{
            String jwt = getJwtFromRequest(request);
            if(StringUtils.hasText(jwt) && tokenProvider.validateToken(jwt)){
                Long userId = tokenProvider.getUserIdToken(jwt);
                UserDetails userDetails = customUserDetailsService.loadUserById(userId);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null,userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
    
    
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        catch(Exception ex){
            logger.error("No se pudo realizar el proceso de autenticación con los datos ingresados", ex);
        }

        filterChain.doFilter(request, response);
        
        
    }


    public String getJwtFromRequest(HttpServletRequest request){

        String token = request.getHeader("Authorization");
        if(existsJwtToken(request,token)){
            token = token.replace(PREFIX, "");
            return token;
        }
        return null;
    }

    private boolean existsJwtToken(HttpServletRequest request,String token){
        if(StringUtils.hasText(token) && token.startsWith(PREFIX)){
            return true;

            //ret
        }
        return false;

    }
    
}
