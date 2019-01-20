package ru.geekbrains.pocket.backend.config;

import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import org.springframework.stereotype.Component;

/**
 * ServletContext initializer for Spring Security specific configuration such as
 * the chain of Spring Security filters.
 */

@Component
public class WebSecurityInitializer extends AbstractSecurityWebApplicationInitializer {
}
