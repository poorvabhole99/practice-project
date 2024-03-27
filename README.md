# Practice Project

This is a Spring Boot project for authentication and authorization.

## Table of Contents

- [User Entity](#user-entity)
- [APIs](#apis)
- [Authentication](#authentication)
    - [Dependencies](#dependencies)
    - [PasswordEncoder](#passwordencoder)
    - [DaoAuthenticationProvider](#daoauthenticationprovider)
    - [SecurityConfig](#securityconfig)
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

### DaoAuthenticationProvider
Information about DaoAuthenticationProvider and how to configure it.

### SecurityConfig
Details about the SecurityConfig class and its configuration for authentication.





