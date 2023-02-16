package com.example.finalproject.filter;

import com.example.finalproject.command.admin.AdminChangeProductCommand;
import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "AuthorizationCommandAdminFilter")
public class AuthorizationCommandAdminFilter implements Filter {
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);
    List<String> adminListCommand = new ArrayList<>();
    public void init(FilterConfig config) throws ServletException {
        adminListCommand = new ArrayList<>();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthorizationCommandAdminFilter is started");
        boolean isRedirectedToLoginPage = false;
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String command = req.getParameter("command");
        logger.info("command is " + command);
        String userRole = Redirect.getUserRole(req, res);
        logger.info("User role is " + userRole);
        if(command != null){
            if (isContainCommand(getAdminListCommand(), command)){
                logger.info("Checking is user has access to this command (Admin command)");
                if (userRole == null || !userRole.equals("admin")){
                    logger.info("User hasn't access to this command");
                    if (!req.getRequestURI().endsWith("login.jsp")) {
                        isRedirectedToLoginPage = true;
                        logger.info("Calling method Redirect.redirectToLoginPage");
                        Redirect.redirectToLoginPage(req, res, "Log in as admin to get access to this page");
                    }
                }
            }
        }

        if (!isRedirectedToLoginPage)
            chain.doFilter(request, response);
    }

    private List<String> getAdminListCommand(){
        adminListCommand.add("ADMIN_ADD_PRODUCT");
        adminListCommand.add("ADMIN_CUSTOMER_CONTROLLER");
        adminListCommand.add("ADMIN_ORDER_CONTROLLER");
        adminListCommand.add("ADMIN_PRODUCT_CONTROLLER");
        adminListCommand.add("ADMIN_CHANGE_PRODUCT");
        return adminListCommand;
    }

    private boolean isContainCommand(List<String> stringList, String command){
        return stringList.stream().anyMatch(command::equalsIgnoreCase);
    }
}