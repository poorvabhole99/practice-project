# Practice Project

This is a Spring Boot project for authentication and authorization.

## Table of Contents

- [User Entity](#user-entity)
- [APIs](#apis)
- [Authentication](#authentication)
    - [Dependencies](#dependencies)
    - [PasswordEncoder](#passwordencoder)
    - [UserDetailsServiceImpl](#userDetailsServiceImpl)
    - [DaoAuthenticationProvider](#daoauthenticationprovider)
    - [SecurityConfig](#securityconfig)
      - [CORSAndCSRF](#cors-and-csrf)
- [Authorization](#authorization)


## User Entity

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user")
public class User {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email_id")
    @Email(message = "Email Id is invalid")
    @NotBlank(message = "Email Id can't be blank")
    @NotNull(message = "Value can't be null")
    private String emailId;

    @Column(name = "first_name")
    @Size(min = 3, message = "First Name can't be less than 3 letters")
    @NotBlank(message = "First name can't be blank")
    @NotNull(message = "Value can't be null")
    private String firstName;

    @Column(name = "last_name")
    @NotBlank(message = "Last name can't be blank")
    private String lastName;

    @Column(name = "date_of_birth")
    @NotBlank(message = "Date of birth can't be blank")
    private String dateOfBirth;

    @Column(name = "password")
    @NotBlank(message = "Password can't be blank")
    private String password;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "roles")
    private Set<String> roles;
}
```

## Authentication

- **Definition:** Authentication is the process of confirming the identity of a user or system entity before granting access to resources.

- **Verification of Credentials:** It involves verifying provided credentials, such as usernames and passwords, against stored records or an authentication server.

- **Purpose:** The primary purpose is to ensure that only authorized individuals or entities gain access to sensitive information or services.

- **Enhancing Security:** Authentication enhances security by preventing unauthorized access and protecting against potential threats and data breaches.


### Dependencies
List of dependencies required for authentication.
```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
```

### PasswordEncoder


A password encoder is a component used in software systems, particularly in authentication processes, to securely hash passwords before storing them in a database or comparing them with hashed passwords during authentication. Here's why we need it:

- **Security:** Storing passwords in plaintext is highly insecure. Passwords should be hashed before storage to prevent unauthorized access in case the database is compromised. A password encoder hashes passwords using cryptographic algorithms, making it extremely difficult for attackers to reverse-engineer the original passwords.

- **Protection against vulnerabilities:** Hashing passwords helps protect against various security vulnerabilities such as SQL injection attacks or data breaches. Even if an attacker gains access to the database, they cannot retrieve the original passwords as they are stored in hashed form.

- **Compliance:** Many regulatory standards and best practices mandate the secure storage of passwords. Using a password encoder helps organizations comply with these standards and demonstrate a commitment to data security.

- **Ease of use:** Password encoders are often integrated into authentication frameworks or libraries, making it easy for developers to implement secure password handling without having to write custom code.

Overall, a password encoder is essential for ensuring the security and integrity of user passwords in software systems.


```java
/**
 * Configuration method for providing a bean of type PasswordEncoder.
 * This bean is responsible for encoding passwords using the BCrypt hashing algorithm,
 * which is a widely used and secure method for password hashing.
 *
 * @return A BCryptPasswordEncoder instance, which implements the PasswordEncoder interface.
 *         This encoder can be used to securely hash passwords before storing them in a database
 *         or comparing them with hashed passwords during authentication.
 */
@Bean
public PasswordEncoder passwordEncoder() {
    // Return a new instance of BCryptPasswordEncoder, which implements the PasswordEncoder interface
    return new BCryptPasswordEncoder();
}
```

#### How to use it in spring boot ?
Example
```java
@Service
public class UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void registerUser(String username, String password) {
        // Encode the password before storing it in the database
        String encodedPassword = passwordEncoder.encode(password);
        
        // Save the user with the encoded password
        userRepository.save(new User(username, encodedPassword));
    }
}
```
## UserDetailsServiceImpl

This is a Spring service class that implements the `UserDetailsService` interface. It's typically used in conjunction with Spring Security for user authentication and authorization.

### Class Overview

- **Class Name**: `UserDetailsServiceImpl`
- **Implements**: `UserDetailsService`

### Dependencies

- **Autowired Field**: `UserRepository`

### Code 

```java
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Autowired
  private UserRepository userRepository;

  public UserDetailsServiceImpl(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    Optional<User> userInfo = this.userRepository.findByEmailId(username);
    return userInfo.map(CustomUserDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("Invalid Username"));
  }
}
```
### Method Overview

This method(loadUserByUsername), part of the `UserDetailsService` interface, is used for user authentication.

- **Parameters**:
  - `username`: Represents the email ID of the user.

- **Functionality**:
  1. Calls `findByEmailId` method on `UserRepository` to retrieve user information based on the provided email ID, wrapped in an `Optional`.
  2. Maps user information to a `CustomUserDetails` object, representing user details.
  3. Throws `UsernameNotFoundException` if no user is found with the provided email ID.
  4. Otherwise, returns the `CustomUserDetails` object containing the user information.

### DaoAuthenticationProvider
```java
/**
* Configuration method for providing an `AuthenticationProvider` bean.
* This bean configures a `DaoAuthenticationProvider`, which is responsible for authenticating users
* based on information retrieved from a `UserDetailsService` and using a `PasswordEncoder` for password validation.
*
* @return An instance of `AuthenticationProvider`, specifically a `DaoAuthenticationProvider`,
*         configured with a `UserDetailsService` and a `PasswordEncoder`.
*         This provider can be used in Spring Security configuration to authenticate users
*         based on credentials stored in a database or other data source.
*/
@Bean
public AuthenticationProvider daoAuthenticationProvider() {
// Create a new instance of `DaoAuthenticationProvider`
DaoAuthenticationProvider provider = new DaoAuthenticationProvider();

// Set the `UserDetailsService` to be used by the provider for retrieving user details
provider.setUserDetailsService(userDetailsService);

// Set the `PasswordEncoder` to be used by the provider for password validation
provider.setPasswordEncoder(passwordEncoder());

// Return the configured `DaoAuthenticationProvider`
return provider;
}
```

### SecurityConfig
```java
/**
* Configuration method for defining a `SecurityFilterChain` bean.
* This bean configures the security filters to be applied to HTTP requests,
* specifying authentication, authorization rules, and exception handling.
*
* @param http The `HttpSecurity` object provided by Spring Security for configuring security settings.
* @return A `SecurityFilterChain` configured with specified security settings for HTTP requests.
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
  // Require authentication for endpoints matching "/user/getUser/**"
  .requestMatchers("/user/getUser/**").authenticated())
  // Configure authentication provider to be used for authentication
  .authenticationProvider(daoAuthenticationProvider())
  // Use HTTP Basic authentication with default settings
  .httpBasic(withDefaults())
  // Configure exception handling for authentication failures
  .exceptionHandling(ex -> ex.authenticationEntryPoint(point));

// Build and return the configured `HttpSecurity` object as a `SecurityFilterChain`
return http.build();
}

```

## Method Overview

This method configures a `SecurityFilterChain` bean, which defines security filters for HTTP requests in a Spring Security application.

### Parameters

- `http`: The `HttpSecurity` object provided by Spring Security for configuring security settings.

### Functionality

1. **Disable CSRF and CORS**:
  - Disables CSRF protection and CORS (Cross-Origin Resource Sharing) to simplify configuration.

2. **Authorization Rules**:
  - Configures authorization rules using method chaining:
    - Permits access to the "/user/create" endpoint without authentication.
    - Requires authentication for endpoints matching "/user/getUser/**".

3. **Authentication Provider**:
  - Configures the authentication provider to be used for authentication.

4. **HTTP Basic Authentication**:
  - Sets up HTTP Basic authentication with default settings.

5. **Exception Handling**:
  - Configures exception handling for authentication failures.

### Return Value

- Returns the configured `HttpSecurity` object as a `SecurityFilterChain`.

## CORS and CSRF

### CORS (Cross-Origin Resource Sharing)

**Purpose**: CORS is a security feature implemented by web browsers to prevent cross-origin requests, which can potentially lead to security vulnerabilities.

**When to Disable**:
- If your application doesn't need to allow cross-origin requests from other domains or if it's not a web API accessed by other client applications, you might choose to disable CORS to simplify configuration and avoid unnecessary complexity.
- However, if your application needs to serve resources to clients from different origins, you would typically configure CORS to allow specific origins, methods, and headers.

### CSRF (Cross-Site Request Forgery) Protection

**Purpose**: CSRF protection guards against attacks where a malicious website can make requests to your application on behalf of an authenticated user.

**When to Disable**:
- CSRF protection is often disabled for endpoints that are not state-changing or sensitive.
- Sometimes, for simpler applications or in specific contexts where CSRF attacks are less likely or mitigated by other means (such as session-based authentication), disabling CSRF protection might be acceptable.
- However, disabling CSRF protection should be done with caution and only after careful consideration of the security implications. If your application handles sensitive operations or user data, CSRF protection should typically be enabled.









