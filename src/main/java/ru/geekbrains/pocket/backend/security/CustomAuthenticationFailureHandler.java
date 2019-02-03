package ru.geekbrains.pocket.backend.security;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Calendar;

@Component
public class CustomAuthenticationFailureHandler extends SimpleUrlAuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException exception)
            throws IOException, ServletException {

        super.onAuthenticationFailure(request, response, exception);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());

        String jsonPayload = "{\"message\" : \"%s\", \"timestamp\" : \"%s\" }";
        response.getOutputStream().println(String.format(jsonPayload, exception.getMessage(), Calendar.getInstance().getTime()));
    }
}
