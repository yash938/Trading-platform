package com.tradingplatform.config;

import com.tradingplatform.security.AuthEntryPoint;
import com.tradingplatform.security.JwtFilter;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class SecurityFilterConfig {

    @Autowired
    private AuthEntryPoint authEntryPoint;

    @Autowired
    private JwtFilter jwtAuthenticationFilter;




    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {



        httpSecurity.csrf(csrf -> csrf.disable())
                .cors(httpSecurityCorsConfigurer -> httpSecurityCorsConfigurer.configurationSource(new CorsConfigurationSource() {
                    @Override
                    public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                        CorsConfiguration configuration = new CorsConfiguration();

                        configuration.setAllowedOrigins(List.of("*"));
                        configuration.setAllowCredentials(true);
                        configuration.setAllowedMethods(List.of("GET", "POST", "PUT", "DELETE"));
                        configuration.setAllowedHeaders(List.of("Authorization","Content-Type"));

                        configuration.setMaxAge(4000L);
                        return configuration;
                    }
                }))
                .authorizeHttpRequests(auth ->
                        auth.requestMatchers("/user/**").authenticated()
                                .requestMatchers("/auth/**").permitAll()

                                .anyRequest().permitAll()


                );


//        httpSecurity.httpBasic(Customizer.withDefaults());
        httpSecurity.exceptionHandling(ex ->ex.authenticationEntryPoint(authEntryPoint));
        httpSecurity.sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        httpSecurity.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);


        return httpSecurity.build();
    }


    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }
}
