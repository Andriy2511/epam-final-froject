package com.example.finalproject.filter;

import com.example.finalproject.command.admin.AdminChangeProductCommand;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "AuthorizationPageUserFilter")
public class AuthorizationPageUserFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthorizationPageUserFilter is started");
        boolean isRedirectedToLoginPage = false;
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String page = req.getServletPath();
        if(page.startsWith("/user/")){
            String userRole = Redirect.getUserRole(req, res);
            if(userRole == null || !userRole.equals("user")) {
                if(!req.getRequestURI().endsWith("login.jsp")) {
                    isRedirectedToLoginPage = true;
                    logger.info("User role is " + userRole + ". Calling method Redirect.redirectToLoginPage");
                    Redirect.redirectToLoginPage(req, res, "Log in as user to get access to this page");
                }
            }
        }
        if (!isRedirectedToLoginPage)
            chain.doFilter(request, response);
    }
}