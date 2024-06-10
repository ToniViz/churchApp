package com.app.church.church.services;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.church.church.entities.users.Login;
import com.app.church.church.repository.LoginRepository;

@Service
public class JpaUserLoginDetailsService implements UserDetailsService {


    @Autowired
    private LoginRepository loginRepository;

    @Transactional(readOnly = true)
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Login> loginO = this.loginRepository.findByUsername(username);

        if(loginO.isEmpty()){
            throw new UsernameNotFoundException(String.format("username %s no existe en la BBDD", username));
        }
        Login login = loginO.orElseThrow();

        List<GrantedAuthority> authorities = login.getRoles().stream().map(role -> 
            new SimpleGrantedAuthority(role.getRole())
        ).collect(Collectors.toList());

        System.out.println("Aquí no existen errores, en loadUserByUsername");
        System.out.println("El nombre en load es:"+ login.getUsername());
        System.out.println("El nombre en load es:"+ login.getPassword());
        authorities.forEach(au -> {
            System.out.println("Los permisos son:"+ au.getAuthority());
        });
        
       return new org.springframework.security.core.userdetails.User(login.getUsername(),
       login.getPassword(), login.isEnabled(), 
       true,
        true, 
        true, authorities );
    }

}
