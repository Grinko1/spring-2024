package org.spring.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityBeans {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http
                .authorizeHttpRequests(authorizeHttpRequests-> authorizeHttpRequests.requestMatchers(
                        HttpMethod.POST,"/api/products")
                        .hasAuthority("SCOPE_edit_catalog")
                        .requestMatchers(HttpMethod.PATCH, "/api/products/{productId:\\d+}/edit")
                        .hasAuthority("SCOPE_edit_catalog")
                        .requestMatchers(HttpMethod.DELETE, "/api/products/{productId:\\d+}")
                        .hasAuthority("SCOPE_edit_catalog")
                        .requestMatchers(HttpMethod.GET)
                        .hasAuthority("SCOPE_view_catalog")
                        .anyRequest()
                        .denyAll()
                )
                .csrf(CsrfConfigurer::disable)
                .sessionManagement(sessionManagement -> sessionManagement
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .oauth2ResourceServer(oauth2ResourceServer -> oauth2ResourceServer.jwt(Customizer.withDefaults()) )
                .build();
    }
}
