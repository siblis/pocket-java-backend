package ru.geekbrains.pocket.backend.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import ru.geekbrains.pocket.backend.domain.db.User;
import ru.geekbrains.pocket.backend.service.UserService;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

//https://www.baeldung.com/securing-a-restful-web-service-with-spring-security

@Component
public class MySavedRequestAwareAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();

    @Autowired
    private UserService userService;

    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication)
            throws ServletException, IOException {

        final SavedRequest savedRequest = requestCache.getRequest(request, response);

        String emailUser = authentication.getName();
        System.out.println("authentication user name (email) = " + emailUser);

        User user = userService.getUserByEmail(emailUser);
        user.setUsername(user.getEmail());

        // now place in the session
        HttpSession session = request.getSession();
        session.setAttribute("user", user);

        if (savedRequest == null) {
            String uri = request.getRequestURI();
            if (uri.equals("/login") || uri.equals("/authenticateTheUser")) {
                response.sendRedirect(request.getContextPath() + "/web");
                //clearAuthenticationAttributes(request);
                //this.handle(request, response, authentication);
                super.onAuthenticationSuccess(request, response, authentication);
            } else {
                //response.setStatus(HttpStatus.OK.value());
                clearAuthenticationAttributes(request);
            }
            return;
        }
//        final String targetUrlParameter = getTargetUrlParameter();
//        if (isAlwaysUseDefaultTargetUrl() || (targetUrlParameter != null && StringUtils.hasText(request.getParameter(targetUrlParameter)))) {
//            requestCache.removeRequest(request, response);
//            String uri = request.getRequestURI();
//            if (uri.equals("/login") || uri.equals("/authenticateTheUser") || uri.startsWith("/authenticateTheUser"))
//                super.onAuthenticationSuccess(request, response, authentication);
//            else
//                clearAuthenticationAttributes(request);
//            return;
//        }
//
//        clearAuthenticationAttributes(request);

        // Use the DefaultSavedRequest URL
        // final String targetUrl = savedRequest.getRedirectUrl();
        // logger.debug("Redirecting to DefaultSavedRequest Url: " + targetUrl);
        // getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }

    public void setRequestCache(final RequestCache requestCache) {
        this.requestCache = requestCache;
    }
}
