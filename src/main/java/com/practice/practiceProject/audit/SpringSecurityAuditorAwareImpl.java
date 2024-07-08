package com.practice.practiceProject.audit;

import java.util.Optional;
import org.springframework.data.domain.AuditorAware;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;


/**
 * Implementation of the Spring Security AuditorAware interface for obtaining the current auditor
 * (user) responsible for auditing actions. This class retrieves the username of the currently
 * authenticated user from the Spring Security context.
 */


public class SpringSecurityAuditorAwareImpl implements AuditorAware<String> {


  /**
   * Retrieves an Optional containing the username of the currently authenticated user, if
   * available, from the Spring Security context.
   *
   * @return An Optional containing the username of the current auditor, or an empty Optional if no
   * authenticated user is found.
   */


  @Override
  public Optional<String> getCurrentAuditor() {
    // Retrieve the current authentication object from the Spring Security context
    final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

    // Check if authentication is null or if the user is not authenticated
    if (authentication == null || !authentication.isAuthenticated()) {
      // No authenticated user found, return an empty Optional
      return Optional.empty();
    }

    // Retrieve the username of the authenticated user
    final String username = authentication.getName();

    // Return an Optional containing the username of the current auditor
    return Optional.of(username);
  }
}