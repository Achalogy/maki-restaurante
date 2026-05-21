package com.maki.web.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

  @Autowired
  private JwtAuthEntryPoint jwtAuthEntryPoint;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .csrf(AbstractHttpConfigurer::disable)
      .headers(headers -> headers.frameOptions(frame -> frame.disable()))
      .authorizeHttpRequests(requests ->
        requests
          // Consola H2
          .requestMatchers("/h2/**")
          .permitAll()

          // Endpoints públicos (Login y Registro de Clientes)
          .requestMatchers(
            HttpMethod.POST,
            "/api/v1/client",
            "/api/v1/client/log-in"
          )
          .permitAll()
          .requestMatchers(
            HttpMethod.POST,
            "/api/v1/admin/log-in",
            "/api/v1/operator/log-in",
            "/api/v1/auth/log-in"
          )
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/api/v1/health")
          .permitAll()

          // Consultas públicas (Ver platos, categorías y adicionales)
          .requestMatchers(HttpMethod.GET, "/api/v1/plate/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/api/v1/category/**")
          .permitAll()
          .requestMatchers(HttpMethod.GET, "/api/v1/additional/**")
          .permitAll()

          // Acceso exclusivo de ADMINISTRADOR
          .requestMatchers("/api/v1/admin/**")
          .hasAuthority("ADMIN")
          .requestMatchers("/api/v1/operator/**")
          .hasAuthority("ADMIN")
          .requestMatchers("/api/v1/client")
          .hasAuthority("ADMIN")
          .requestMatchers("/api/v1/admin")
          .hasAuthority("ADMIN")
          .requestMatchers("/api/v1/operator")
          .hasAuthority("ADMIN")
          .requestMatchers(
            HttpMethod.POST,
            "/api/v1/plate/**",
            "/api/v1/category/**",
            "/api/v1/additional/**"
          )
          .hasAuthority("ADMIN")
          .requestMatchers(
            HttpMethod.DELETE,
            "/api/v1/plate/**",
            "/api/v1/category/**",
            "/api/v1/additional/**",
            "/api/v1/purchase-order/**"
          )
          .hasAuthority("ADMIN")

          // Acceso CLIENTE (Realizar pedidos y ver sus datos)
          .requestMatchers(HttpMethod.POST, "/api/v1/purchase-order/client/**")
          .hasAuthority("CLIENT")
          .requestMatchers("/api/v1/client/**")
          .hasAnyAuthority("CLIENT", "ADMIN")

          // Acceso OPERADOR y ADMIN (Gestión de pedidos y entregas)
          .requestMatchers("/api/v1/delivery/**")
          .hasAnyAuthority("OPERATOR", "ADMIN")
          .requestMatchers("/api/v1/order-details/client/**")
          .hasAnyAuthority("OPERATOR", "ADMIN", "CLIENT")
          .requestMatchers("/api/v1/order-details/**")
          .hasAnyAuthority("OPERATOR", "ADMIN")
          .requestMatchers(HttpMethod.POST, "/api/v1/purchase-order/{id}")
          .hasAnyAuthority("OPERATOR", "ADMIN")
          .requestMatchers(HttpMethod.GET, "/api/v1/purchase-order/**")
          .hasAnyAuthority("CLIENT", "OPERATOR", "ADMIN")

          .anyRequest()
          .authenticated()
      )
      .exceptionHandling(exception ->
        exception.authenticationEntryPoint(jwtAuthEntryPoint)
      );

    http.addFilterBefore(
      jwtAuthenticationFilter(),
      UsernamePasswordAuthenticationFilter.class
    );

    return http.build();
  }

  @Bean
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationManager authenticationManager(
    AuthenticationConfiguration authenticationConfiguration
  ) throws Exception {
    return authenticationConfiguration.getAuthenticationManager();
  }

  @Bean
  public JWTAuthenticationFilter jwtAuthenticationFilter() {
    return new JWTAuthenticationFilter();
  }
}
