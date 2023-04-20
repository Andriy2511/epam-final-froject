package com.example.finalproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

/**
 * Checks whether the user has access to jsp pages
 */
@WebFilter(filterName = "AuthorizationPageUserFilter")
public class AuthorizationPageUserFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AuthorizationPageUserFilter.class);
    public void init(FilterConfig config) {
    }

    public void destroy() {
    }

    /**
     * Checks if the user has access to the page. If the user's role isn't "user", the method redirects to the authorization page.
     * User pages are defined in the "user" folder.
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthorizationPageUserFilter is started");
        boolean isRedirectedToLoginPage = false;
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String page = req.getServletPath();
        if(page.startsWith("/user/")){
            String userRole = Redirect.getUserRole(req);
            if(userRole == null || !userRole.equals("user")) {
                if(!req.getRequestURI().endsWith("login.jsp")) {
                    isRedirectedToLoginPage = true;
                    logger.info("User role is {}. Calling method Redirect.redirectToLoginPage", userRole);
                    Redirect.redirectToLoginPage(req, res, "locale.LogInAsUser");
                }
            }
        }
        if (!isRedirectedToLoginPage)
            chain.doFilter(request, response);
    }
}