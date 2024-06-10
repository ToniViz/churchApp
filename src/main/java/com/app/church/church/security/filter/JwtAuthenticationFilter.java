package com.app.church.church.security.filter;

import java.io.IOException;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.app.church.church.entities.users.Login;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import static com.app.church.church.security.TokenConfig.*;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
                System.out.println("Entra en attempAutenticaion....");
        Login login = null;
        String username = null;
        String password = null;

        try {
            login = new ObjectMapper().readValue(request.getInputStream(),
             Login.class);
             username = login.getUsername();
             System.out.println("Seguimos en Attemp, verificando datos");
             System.out.println("El nombre de usuario es:"+username);
             password = login.getPassword();
             System.out.println("El password en attempAuth:"+ password);
        } catch (IOException e) {
            throw new AuthenticationServiceException("Error al validar el usuario", e);
        }
        UsernamePasswordAuthenticationToken authenticationToken =
        new UsernamePasswordAuthenticationToken(username, password);
        return this.authenticationManager.authenticate(authenticationToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
            Authentication authResult) throws IOException, ServletException {
                System.out.println("Entra y valida los datos de este success");
            org.springframework.security.core.userdetails.User login =
            (org.springframework.security.core.userdetails.User)authResult.getPrincipal();

            String username = login.getUsername();
            
            Collection<? extends GrantedAuthority> roles = authResult.getAuthorities();

            Claims claims = Jwts.claims().add("authorities", new ObjectMapper().writeValueAsString(roles))
            .add("username",username)
            .build();

            String jws = Jwts.builder()
            .subject(username).claims(claims)
            .expiration(new Date(System.currentTimeMillis() + 3600000))
            .issuedAt(new Date()).signWith(SECRET_KEY).compact();

            response.addHeader(HEADER_AUTHORIZATION, PREFIX_TOKEN + jws);
            //Return as JSON file
            Map<String, String> body = new HashMap<>();
            body.put("token", jws);
            body.put("username", username);
            body.put("message", String.format("Hola %s has iniciado sesión el usuario", username));
            response.getWriter().write(new ObjectMapper()
            .writeValueAsString(body));
            response.setContentType(CONTENT_TYPE);
            response.setStatus(200);

    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
            AuthenticationException failed) throws IOException, ServletException {
                System.out.println("Entra y NO valida los datos de este success");
        Map<String, String> body = new HashMap<>();
        body.put("message", "Error en la autenticación. Username o password incorrectos");
        body.put("error", failed.getMessage());

        response.getWriter().write(new ObjectMapper()
        .writeValueAsString(body));
        response.setStatus(401);
        response.setContentType(CONTENT_TYPE);
    }


    
}
