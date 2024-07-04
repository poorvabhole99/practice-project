package com.practice.practiceProject.audit;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * Configuration class for enabling JPA auditing and defining an AuditorAware bean. This
 * configuration enables automatic auditing of JPA entities and provides a custom implementation for
 * obtaining the current auditor (user) responsible for auditing actions.
 */
@Configuration
@EnableJpaAuditing
public class AuditConfiguration {

    /**
     * Defines a bean responsible for providing an implementation of AuditorAware interface. This bean
     * is used by Spring Data JPA to determine the current auditor (user) responsible for auditing
     * actions on entities.
     *
     * @return An implementation of AuditorAware<String> interface that retrieves the current auditor.
     */
    @Bean
    public AuditorAware<String> auditorProvider() {
        // Return a new instance of SpringSecurityAuditorAwareImpl, which implements AuditorAware<String>
        return new SpringSecurityAuditorAwareImpl();
    }

}