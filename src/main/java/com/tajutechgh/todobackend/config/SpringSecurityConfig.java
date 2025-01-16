package com.tajutechgh.todobackend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;

    public SpringSecurityConfig(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize) -> {

//            authorize.requestMatchers(HttpMethod.GET, "/api/v1/todos/all").hasAnyRole("USER", "ADMIN");
//            authorize.requestMatchers(HttpMethod.GET, "/api/v1/todos/get/{id}").hasAnyRole("USER", "ADMIN");
//            authorize.requestMatchers(HttpMethod.POST, "/api/v1/todos/create").hasRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.PUT, "/api/v1/todos/update/{id}").hasRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/todos/delete/{id}").hasRole("ADMIN");
//            authorize.requestMatchers(HttpMethod.PATCH, "/api/v1/todos/complete/{id}").hasAnyRole("USER", "ADMIN");
//            authorize.requestMatchers(HttpMethod.PATCH, "/api/v1/todos/incomplete/{id}").hasAnyRole("USER", "ADMIN");
            authorize.requestMatchers("/api/v1/auth/**").permitAll();
            authorize.anyRequest().authenticated();

        }).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {

        return authenticationConfiguration.getAuthenticationManager();
    }

    // in-memory users auth
//    @Bean
//    public UserDetailsService userDetailsService() {
//        UserDetails user = User.builder()
//                .username("user")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.builder()
//                .username("admin")
//                .password(passwordEncoder().encode("password"))
//                .roles("USER", "ADMIN")
//                .build();
//
//        return new InMemoryUserDetailsManager(user, admin);
//    }
}
