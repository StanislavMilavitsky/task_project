package by.milavitsky.task_poject.config;

import by.milavitsky.task_poject.entity.Role;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.springframework.security.config.Customizer.withDefaults;


@Configuration
@EnableMethodSecurity
public class SecurityConfiguration {

    /*@Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
         http
                .csrf().disable()
                .authorizeHttpRequests(urlConfig -> urlConfig
                       // .antMatchers( "/users/registration", "v3/api-docs/**", "swagger-ui/**").authenticated()
                       // .antMatchers("/users/{\\d+}/delete", "/users/{\\d+}/find-by-id").hasAuthority(Role.ADMIN.getAuthority())
                        .anyRequest().permitAll())
                        .httpBasic(withDefaults());
        return http.build();
    }*/

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

}
