package com.tajutechgh.todobackend.config;

import com.tajutechgh.todobackend.security.jwt.JwtAuthenticationEntrypoint;
import com.tajutechgh.todobackend.security.jwt.JwtAuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SpringSecurityConfig {

    private UserDetailsService userDetailsService;
    private JwtAuthenticationEntrypoint authenticationEntrypoint;
    private JwtAuthenticationFilter authenticationFilter;

    public SpringSecurityConfig(UserDetailsService userDetailsService, JwtAuthenticationEntrypoint authenticationEntrypoint,
                                JwtAuthenticationFilter authenticationFilter) {
        this.userDetailsService = userDetailsService;
        this.authenticationEntrypoint = authenticationEntrypoint;
        this.authenticationFilter = authenticationFilter;
    }

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable()).authorizeHttpRequests((authorize) -> {

            authorize.requestMatchers(HttpMethod.GET, "/api/v1/todos/all").hasAnyAuthority("USER", "ADMIN");
            authorize.requestMatchers(HttpMethod.GET, "/api/v1/todos/get/{id}").hasAnyAuthority( "ADMIN", "USER");
            authorize.requestMatchers(HttpMethod.POST, "/api/v1/todos/create").hasAnyAuthority("ADMIN", "USER");
            authorize.requestMatchers(HttpMethod.PUT, "/api/v1/todos/update/{id}").hasAnyAuthority("ADMIN", "USER");
            authorize.requestMatchers(HttpMethod.DELETE, "/api/v1/todos/delete/{id}").hasAnyAuthority("ADMIN", "USER");
            authorize.requestMatchers(HttpMethod.PATCH, "/api/v1/todos/complete/{id}").hasAnyAuthority("USER", "ADMIN");
            authorize.requestMatchers(HttpMethod.PATCH, "/api/v1/todos/incomplete/{id}").hasAnyAuthority("USER", "ADMIN");
            authorize.requestMatchers(HttpMethod.POST, "/api/v1/auth/login", "/api/v1/auth/register").permitAll();
            authorize.requestMatchers(HttpMethod.OPTIONS, "/**").permitAll();
            authorize.anyRequest().authenticated();

        }).httpBasic(Customizer.withDefaults());

        http.exceptionHandling((exception) -> exception.authenticationEntryPoint( authenticationEntrypoint));

        http.addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
