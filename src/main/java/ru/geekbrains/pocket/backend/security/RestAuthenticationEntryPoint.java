package ru.geekbrains.pocket.backend.security;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * The Entry Point will not redirect to any sort of Login - it will return the 401
 */
@Component
public final class RestAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(
            final HttpServletRequest request,
            final HttpServletResponse response,
            final AuthenticationException authException) throws IOException {

        String uri = request.getRequestURI();
        if (uri.equals("/") || uri.equals("/authenticateTheUser") || uri.startsWith("/web") || uri.startsWith("/admin"))
            response.sendRedirect(request.getContextPath() + "/login");
        else
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Unauthorized");

    }

}