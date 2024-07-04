package com.practice.practiceProject.Security;

import com.practice.practiceProject.entities.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Custom implementation of Spring Security UserDetails interface.
 * This class represents user details used for authentication and authorization in Spring Security.
 * It encapsulates information about a user, including username, password, and authorities (roles).
 */
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class CustomUserDetails implements UserDetails {

    /**
     * The username of the user.
     */
    private String username;

    /**
     * The password of the user.
     */
    private String password;

    /**
     * Collection of authorities (roles) granted to the user.
     */
    Collection<? extends GrantedAuthority> authorities;

    /**
     * Constructs a CustomUserDetails object based on the provided User object.
     *
     * @param user The User object from which to extract user details.
     */
    public CustomUserDetails(User user) {
        this.username = user.getEmailId();
        this.password = user.getPassword();
        List<GrantedAuthority> auths = new ArrayList<>();

        // Convert user roles to GrantedAuthority objects
        for (String role : user.getRoles()) {
            auths.add(new SimpleGrantedAuthority(role.toUpperCase()));
        }
        this.authorities = auths;
    }

    /**
     * Retrieves the authorities (roles) granted to the user.
     *
     * @return Collection of GrantedAuthority objects representing the user's roles.
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    /**
     * Retrieves the password of the user.
     *
     * @return The password of the user.
     */
    @Override
    public String getPassword() {
        return this.password;
    }

    /**
     * Retrieves the username of the user.
     *
     * @return The username of the user.
     */
    @Override
    public String getUsername() {
        return this.username;
    }

    /**
     * Indicates whether the user's account has not expired.
     *
     * @return true if the user's account is not expired, false otherwise.
     */
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user's account is not locked.
     *
     * @return true if the user's account is not locked, false otherwise.
     */
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    /**
     * Indicates whether the user's credentials (password) are not expired.
     *
     * @return true if the user's credentials are not expired, false otherwise.
     */
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    /**
     * Indicates whether the user is enabled.
     *
     * @return true if the user is enabled, false otherwise.
     */
    @Override
    public boolean isEnabled() {
        return true;
    }
}

