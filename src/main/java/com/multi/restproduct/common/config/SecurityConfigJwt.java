package com.multi.restproduct.common.config;

import com.multi.restproduct.common.jwt.JwtAccessDeniedHandler;
import com.multi.restproduct.common.jwt.JwtAuthenticationEntryPoint;
import com.multi.restproduct.common.jwt.JwtFilter;
import com.multi.restproduct.common.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@Configuration
@RequiredArgsConstructor
public class SecurityConfigJwt {

    private final TokenProvider tokenProvider;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/", "/main").permitAll()
                        .requestMatchers("/auth/**").permitAll()
                        .requestMatchers("/api/v1/product/**").permitAll()
                        .requestMatchers("/api/v1/products/**").permitAll()

                        .requestMatchers("/products/**", "/css/**", "/productimgs/**").permitAll()

                        .requestMatchers("/api/v1/review/**").permitAll()
                        .requestMatchers("/api/v1/product-management/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/v1/products-management/**").hasAnyRole("ADMIN")
                        .requestMatchers("/api/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/swagger-ui/**", "/v3/api-docs/**", "/actuator/health", "/actuator/health/**").permitAll()
                        .anyRequest().authenticated())
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling(exception -> exception
                        .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                        .accessDeniedHandler(jwtAccessDeniedHandler));

        return http.build();
    }


    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://localhost:3000")); // 모든포트허용 *
        configuration.setAllowedMethods(Arrays.asList("GET", "PUT", "POST", "DELETE")); // 허용할 메서드
        configuration.setAllowedHeaders(Arrays.asList("Content-Type", "Authorization")); // 허용할 헤더
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();  //UrlBasedCorsConfigurationSource를 통해 특정 URL 패턴에 규칙을 등록
        source.registerCorsConfiguration("/**", configuration);

        return source;

    }
}
