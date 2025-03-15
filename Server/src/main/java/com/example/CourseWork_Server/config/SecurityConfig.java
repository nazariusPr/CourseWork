package com.example.CourseWork_Server.config;

import static com.example.CourseWork_Server.constant.AppConstants.ANALYZE_LINK;
import static com.example.CourseWork_Server.constant.AppConstants.AUTH_LINK;
import static com.example.CourseWork_Server.constant.AppConstants.LOCATION_LINK;

import com.example.CourseWork_Server.security.filter.ExceptionHandlerFilter;
import com.example.CourseWork_Server.security.filter.JwtAuthenticationFilter;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import java.util.Arrays;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
  private final JwtAuthenticationFilter authenticationFilter;
  private final ExceptionHandlerFilter exceptionFilter;
  private final UserDetailsService userDetailsService;
  private final PasswordEncoder passwordEncoder;

  @Value("${client.url}")
  private String clientUrl;

  @Value("${spring.security.oauth2.client.registration.google.client-id}")
  private String googleClientId;

  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http.csrf(AbstractHttpConfigurer::disable)
        .cors(c -> c.configurationSource(corsConfigurationSource()))
        .authorizeHttpRequests(
            auth ->
                auth.requestMatchers(AUTH_LINK + "/**", LOCATION_LINK + "/**", ANALYZE_LINK + "/**")
                    .permitAll()
                    .requestMatchers(
                        "/v2/api-docs/**",
                        "/v3/api-docs/**",
                        "/swagger.json",
                        "/swagger-ui.html",
                        "/swagger-ui/**",
                        "/swagger-resources/**",
                        "/webjars/**")
                    .permitAll()
                    .anyRequest()
                    .authenticated())
        .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authenticationFilter, UsernamePasswordAuthenticationFilter.class)
        .addFilterBefore(exceptionFilter, JwtAuthenticationFilter.class);

    return http.build();
  }

  @Bean
  CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(true);
    config.setAllowedOrigins(Collections.singletonList(clientUrl));
    config.setAllowedHeaders(Collections.singletonList("*"));
    config.setExposedHeaders(Collections.singletonList("Authorization"));
    config.setAllowedMethods(
        Arrays.asList("OPTIONS", "HEAD", "GET", "PUT", "POST", "DELETE", "PATCH"));
    config.setMaxAge(3600L);

    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", config);
    return source;
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    authProvider.setUserDetailsService(userDetailsService);
    authProvider.setPasswordEncoder(passwordEncoder);
    return authProvider;
  }

  @Bean
  public AuthenticationManager AuthenticationManager(AuthenticationConfiguration configuration)
      throws Exception {
    return configuration.getAuthenticationManager();
  }

  @Bean
  public GoogleIdTokenVerifier verifier() {
    NetHttpTransport transport = new NetHttpTransport();
    JsonFactory jsonFactory = new GsonFactory();

    return new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
        .setAudience(Collections.singletonList(googleClientId))
        .build();
  }
}
