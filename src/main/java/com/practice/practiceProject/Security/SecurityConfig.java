package com.practice.practiceProject.Security;

import static org.springframework.security.config.Customizer.withDefaults;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    private final UserDetailsService userDetailsService;
    private final JwtAuthenticationEntryPoint point;
    private final JwtAuthFilter jwtAuthFilter;
    private final JWTAccessDeniedHandler jwtAccessDeniedHandler;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter, UserDetailsService userDetailsService, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint,JWTAccessDeniedHandler jwtAccessDeniedHandler) {
        this.jwtAuthFilter = jwtAuthFilter;
        this.userDetailsService = userDetailsService;
        this.point = jwtAuthenticationEntryPoint;
        this.jwtAccessDeniedHandler = jwtAccessDeniedHandler;
    }

    private static final String EMPLOYEE = "EMPLOYEE";
    private static final String ADMIN = "ADMIN";


    /**
     * Configuration method for defining a SecurityFilterChain bean.
     * This bean configures the security filters to be applied to HTTP requests,
     * specifying authentication, authorization rules, and exception handling.
     *
     * @param http The HttpSecurity object provided by Spring Security for configuring security settings.
     * @return A SecurityFilterChain configured with specified security settings for HTTP requests.
     * @throws Exception If an error occurs during configuration.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Disable CSRF protection and CORS (Cross-Origin Resource Sharing) to simplify configuration
        http.csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(auth ->
                        auth
                                // Permit access to "/user/create" endpoint without authentication
                                .requestMatchers("/user/create").permitAll()
                                .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
                                // Require authentication for endpoints matching "/user/getUser/**"
                                .requestMatchers(HttpMethod.GET, "/user/getUser/**").hasAnyRole(ADMIN, EMPLOYEE)
                                .requestMatchers(HttpMethod.PUT,"/user/update/**").hasAnyRole(ADMIN)
                                .requestMatchers(HttpMethod.PUT, "/user/activate/**").hasAnyRole(ADMIN, EMPLOYEE)
                                .requestMatchers("/user/delete/**").hasRole(ADMIN)
                                .requestMatchers("/test/**").permitAll())

                // Configure authentication provider to be used for authentication
                .authenticationProvider(daoAuthenticationProvider())
                // Use HTTP Basic authentication with default settings
//        .httpBasic(withDefaults())
                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                // Configure exception handling for authentication failures
                .exceptionHandling(ex -> ex.authenticationEntryPoint(point))
                .exceptionHandling(ex-> ex.accessDeniedHandler(jwtAccessDeniedHandler));

        // Build and return the configured HttpSecurity object as a SecurityFilterChain
        return http.build();
    }


    /**
     * Configuration method for providing a bean of type PasswordEncoder.
     * This bean is responsible for encoding passwords using the BCrypt hashing algorithm,
     * which is a widely used and secure method for password hashing.
     *
     * @return A BCryptPasswordEncoder instance, which implements the PasswordEncoder interface.
     * This encoder can be used to securely hash passwords before storing them in a database
     * or comparing them with hashed passwords during authentication.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        // Return a new instance of BCryptPasswordEncoder, which implements the PasswordEncoder interface
        return new BCryptPasswordEncoder();
    }


    /**
     * Configuration method for providing an AuthenticationProvider bean.
     * This bean configures a DaoAuthenticationProvider, which is responsible for authenticating users
     * based on information retrieved from a UserDetailsService and using a PasswordEncoder for password validation.
     *
     * @return An instance of AuthenticationProvider, specifically a DaoAuthenticationProvider,
     * configured with a UserDetailsService and a PasswordEncoder.
     * This provider can be used in Spring Security configuration to authenticate users
     * based on credentials stored in a database or other data source.
     */
    @Bean
    public AuthenticationProvider daoAuthenticationProvider() {
        // Create a new instance of DaoAuthenticationProvider
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

        // Set the UserDetailsService to be used by the provider for retrieving user details
        provider.setUserDetailsService(userDetailsService);

        // Set the PasswordEncoder to be used by the provider for password validation
        provider.setPasswordEncoder(passwordEncoder());

        // Return the configured DaoAuthenticationProvider
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

}
