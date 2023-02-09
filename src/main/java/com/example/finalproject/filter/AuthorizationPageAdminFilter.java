package com.example.finalproject.filter;

import com.example.finalproject.command.admin.AdminChangeProductCommand;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

@WebFilter(filterName = "AuthorizationPageAdminFilter")
public class AuthorizationPageAdminFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);
    public void init(FilterConfig config) throws ServletException {
    }

    public void destroy() {
    }
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthorizationPageAdminFilter is started");
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String page = req.getServletPath();
        if(page.startsWith("/admin/")){
            String userRole = Redirect.getUserRole(req, res);
            if(userRole == null || !userRole.equals("admin")) {
                if(!req.getRequestURI().endsWith("login.jsp")) {
                    logger.info("User role is " + userRole + ". Calling method Redirect.redirectToLoginPage");
                    Redirect.redirectToLoginPage(req, res, "Log in as admin to get access to this page");
                }
            }
        }
        chain.doFilter(request, response);
    }
}
