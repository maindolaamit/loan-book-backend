package org.hayo.finance.loanbook.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Slf4j
@Configuration
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManagerBean(UserDetailsService service, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(service);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    public static String[] whiteListedEndpoints() {
        return new String[]{
                "/**",
                "/api/v1/auth/**", "/actuator/**",
                "/api/swagger-ui/**", "/v2/api-docs", "/v3/api-docs",
        };
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        log.info("Configuring security filter chain.");
        return http.csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(req -> {
                    req.requestMatchers(whiteListedEndpoints()).permitAll()
                            .requestMatchers("api/v1/customer/").hasRole("CUSTOMER")
                            .requestMatchers("api/v1/admin/").hasRole("ADMIN")
                            .anyRequest().authenticated();
                })
                .logout(logout -> logout.logoutUrl("/api/v1/auth/logout")
                        .clearAuthentication(true)

                )
                .build();
    }
}
