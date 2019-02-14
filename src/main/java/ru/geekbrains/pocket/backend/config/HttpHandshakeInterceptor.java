package ru.geekbrains.pocket.backend.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

//for class WebSocketConfig

@Component
public class HttpHandshakeInterceptor implements HandshakeInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(HttpHandshakeInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                                   Map<String, Object> attributes) throws Exception {

        logger.info("Call beforeHandshake");
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String principal = authentication.getPrincipal().toString();
//            boolean isAuthenticated = authentication.isAuthenticated();
//            String name = authentication.getName();
//            logger.info("name=" + name + ", isAuthenticated=" + isAuthenticated + ", principal=" + principal);
//        }

        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest servletRequest = (ServletServerHttpRequest) request;
            HttpSession session = servletRequest.getServletRequest().getSession();
            attributes.put("sessionId", session.getId());
        }
        return true;
    }

    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response, WebSocketHandler wsHandler,
                               Exception ex) {
        logger.info("Call afterHandshake");
//        if (SecurityContextHolder.getContext().getAuthentication() != null) {
//            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//            String principal = authentication.getPrincipal().toString();
//            boolean isAuthenticated = authentication.isAuthenticated();
//            String name = authentication.getName();
//            logger.info("name=" + name + ", isAuthenticated=" + isAuthenticated + ", principal=" + principal);
//        }
    }

}
