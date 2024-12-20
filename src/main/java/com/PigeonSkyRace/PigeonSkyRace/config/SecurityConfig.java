package com.PigeonSkyRace.PigeonSkyRace.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;

import com.PigeonSkyRace.PigeonSkyRace.keycloak.KeycloakRoleConverter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

        private final AccessDeniedHandler accessDeniedHandler;
        private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
                http.csrf(csrf -> csrf.disable())
                                .cors(cors -> cors.disable())
                                .sessionManagement(session -> session
                                                .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                                .oauth2ResourceServer(oauth2 -> oauth2
                                                .jwt(jwt -> jwt
                                                                .jwtAuthenticationConverter(
                                                                                jwtAuthenticationConverter())))
                                .authorizeHttpRequests(auth -> auth
                                                .requestMatchers(
                                                                "/api/public/**",
                                                                "/api/auth/**",
                                                                "/swagger-ui.html/**",
                                                                "/swagger-ui/**",
                                                                "/v3/api-docs/**")
                                                .permitAll()
                                                .requestMatchers("/api/admin/**").hasRole("ADMIN")
                                                .requestMatchers("/api/organizer/**").hasRole("ORGANIZER")
                                                .requestMatchers("/api/user/**").hasRole("USER")
                                                .requestMatchers("/api/pigeons/**").hasRole("USER")
                                                .anyRequest().authenticated())
                                .exceptionHandling(handling -> handling
                                                .authenticationEntryPoint(customAuthenticationEntryPoint)
                                                .accessDeniedHandler(accessDeniedHandler));

                return http.build();
        }

        @Bean
        public JwtAuthenticationConverter jwtAuthenticationConverter() {
                JwtAuthenticationConverter converter = new JwtAuthenticationConverter();
                converter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
                return converter;
        }

}