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

@WebFilter(filterName = "AuthorizationCommandUserFilter")
public class AuthorizationCommandUserFilter implements Filter {
    List<String> userListCommand;
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);
    public void init(FilterConfig config) throws ServletException {
        userListCommand = new ArrayList<>();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthorizationCommandUserFilter is started");
        boolean isRedirectedToLoginPage = false;
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String command = req.getParameter("command");
        logger.info("command is " + command);
        String userRole = Redirect.getUserRole(req, res);
        logger.info("User role is " + userRole);
        if(command != null){
            if (isContainCommand(getUserListCommand(), command)){
                logger.info("Checking is user has access to this command (User command)");
                if (userRole == null || !userRole.equals("user")){
                    logger.info("User hasn't access to this command");
                    if (!req.getRequestURI().endsWith("login.jsp")) {
                        isRedirectedToLoginPage = true;
                        logger.info("Calling method Redirect.redirectToLoginPage");
                        Redirect.redirectToLoginPage(req, res, "Log in as user to get access to this page");
                    }
                }
            }
        }

        if (!isRedirectedToLoginPage)
            chain.doFilter(request, response);
    }

    private List<String> getUserListCommand(){
        userListCommand.add("USER_ORDER_COMMAND");
        userListCommand.add("USER_CARD_COMMAND");
        return userListCommand;
    }

    private boolean isContainCommand(List<String> stringList, String command){
        return stringList.stream().anyMatch(command::equalsIgnoreCase);
    }
}