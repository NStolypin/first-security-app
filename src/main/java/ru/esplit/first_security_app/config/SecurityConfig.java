package ru.esplit.first_security_app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.EnableGlobalAuthentication;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import ru.esplit.first_security_app.services.PersonDetailsService;

@Configuration
@EnableGlobalAuthentication
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class SecurityConfig {

    private final PersonDetailsService personDetailsService;
    private final SuccessUserHandler successUserHandler;

    public SecurityConfig(PersonDetailsService personDetailsService, SuccessUserHandler successUserHandler) {
        this.personDetailsService = personDetailsService;
        this.successUserHandler = successUserHandler;
    }

    @Bean
    public AuthenticationProvider authenticationProvider(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(personDetailsService);
        provider.setPasswordEncoder(getPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(auth -> auth.requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/user").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/auth/login", "/auth/registration", "/errors",
                        "/css/**", "/js/**", "/img/**", "/lib/**", "/favicon.ico").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form.loginPage("/auth/login")
                        .permitAll()
                        .loginProcessingUrl("/process_login")
                        .successHandler(successUserHandler)
                        .failureUrl("/auth/login?error"))
                .logout(out -> out.logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login"))
                .build();
    }

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
