package fr.diginamic.hello.config;

import fr.diginamic.hello.mappers.UserMapper;
import fr.diginamic.hello.repositories.UserAccountRepository;
import fr.diginamic.hello.services.JwtAuthFilter;
import fr.diginamic.hello.services.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    @Lazy
    JwtAuthFilter jwtAuthFilter;


    // Utilise la base de données pour gérer les utilisateurs
//    @Bean
//    UserDetailsService userDetails(UserAccountRepository userAccountRepository) {
//        return username -> UserMapper.toUserDetails(userAccountRepository.findByUsername(username));
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserAccountService();
    }


    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/", "/login", "/img/**", "/api/login").permitAll()
                        .requestMatchers("/api/**").hasAnyRole("ADMIN", "USER")
                        .requestMatchers("/villes", "/departements", "/logout").authenticated()
                        .requestMatchers("/remove-ville/**", "/remove-dep/**").hasRole("ADMIN")
                        .anyRequest().denyAll())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);

        http
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/villes", true)
                        .permitAll()
                )
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }

    // Ajout du Hasheur de mot de passe
    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


}
