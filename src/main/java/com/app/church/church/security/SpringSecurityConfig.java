package com.app.church.church.security;

import java.util.Arrays;

import java.util.List;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.app.church.church.entities.users.Mapping;
import com.app.church.church.entities.users.Role;
import com.app.church.church.repository.MappingRepository;
import com.app.church.church.security.filter.JwtAuthenticationFilter;
import com.app.church.church.security.filter.JwtValidationFilter;


@Configuration

@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {

    @Autowired
    private AuthenticationConfiguration authenticationConfiguration;

    @Autowired
    private MappingRepository mappingRepository;

    
    @Bean
    AuthenticationManager authenticationManager() throws Exception {
        return this.authenticationConfiguration.getAuthenticationManager();
    }


    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    
     
    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        System.out.println("ENTRA EN FILTERCHAIN");
        http.csrf(csrf -> csrf.disable())
        .cors(cors -> cors.configurationSource(this.corsConfigurationSource()))
        .authorizeHttpRequests(auth -> {
            List<Mapping> permissions = (List<Mapping>) this.mappingRepository.findAll();
            for (Mapping permission : permissions) {
                Set<Role> roles = permission.getRoles();
                String[] rolesNames = roles.stream().map(Role::getRole)
                .map(rolename -> rolename.replace("ROLE_", ""))
                .toArray(String[]::new);
                
                String httpMethod = HttpMethod.valueOf(permission.getType()).name();
                switch (httpMethod) {
                    case "GET":
                        auth.requestMatchers(HttpMethod.GET, permission.getUrl()).hasAnyRole(rolesNames);
                        break;
                    case "POST":
                        auth.requestMatchers(HttpMethod.POST, permission.getUrl()).hasAnyRole(rolesNames);
                        break;
                    case "PUT":
                        auth.requestMatchers(HttpMethod.PUT, permission.getUrl()).hasAnyRole(rolesNames);
                        break;
                    case "DELETE":
                        auth.requestMatchers(HttpMethod.DELETE, permission.getUrl()).hasAnyRole(rolesNames);
                        break;
                    default:
                        throw new IllegalStateException("HTTP method not supported: " + permission.getType());
                }
                
            }
            
            auth.anyRequest().authenticated();
        })
                
                .addFilter(new JwtAuthenticationFilter(this.authenticationManager()))
                .addFilter(new JwtValidationFilter(this.authenticationManager()))
                .sessionManagement(m -> m.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

                return http.build();
                
    }
    
    
    /**
     * Los dos métodos de abajo permiten una conexión desde un front end externo
     * 
     * @return
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource() {

        CorsConfiguration config = new CorsConfiguration();
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        config.setAllowedMethods(Arrays.asList("GET", "POST", "DELETE", "PUT"));
        config.setAllowedHeaders(Arrays.asList("Authorization", "Content-Type"));
        config.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        // Le decimos que se aplica en la aplicación
        source.registerCorsConfiguration("/**", config);
        return source;

    }


     @Bean
    FilterRegistrationBean<CorsFilter> corsFilter() {
        FilterRegistrationBean<CorsFilter> corsBean = new FilterRegistrationBean<>(
            new CorsFilter(this.corsConfigurationSource())
        );
        corsBean.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return corsBean;
    }



}
