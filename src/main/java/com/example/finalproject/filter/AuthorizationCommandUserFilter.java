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

/**
 * Checks whether a user with the role "user" is authorized
 */
@WebFilter(filterName = "AuthorizationCommandUserFilter")
public class AuthorizationCommandUserFilter implements Filter {
    List<String> userListCommand;
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);
    public void init(FilterConfig config) {
        userListCommand = new ArrayList<>();
    }

    public void destroy() {
    }

    /**
     * Checking whether the commands from the request require role verification.
     * If verification is required and the user's role isn't "user", redirects to the login page.
     * @param request ServletRequest
     * @param response ServletResponse
     * @param chain FilterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        logger.info("AuthorizationCommandUserFilter is started");
        boolean isRedirectedToLoginPage = false;
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String command = req.getParameter("command");
        logger.info("Command is {} ", command);
        String userRole = Redirect.getUserRole(req);
        logger.info("User role is {} ", userRole);
        if(command != null){
            if (isContainCommand(getUserListCommand(), command)){
                logger.info("Checking is user has access to this command (User command)");
                if (userRole == null || !userRole.equals("user")){
                    logger.info("User hasn't access to this command");
                    if (!req.getRequestURI().endsWith("login.jsp")) {
                        isRedirectedToLoginPage = true;
                        logger.info("Calling method Redirect.redirectToLoginPage");
                        Redirect.redirectToLoginPage(req, res, "locale.LogInAsUser");
                    }
                }
            }
        }
        if (!isRedirectedToLoginPage)
            chain.doFilter(request, response);
    }

    /**
     * Contains commands that require authorization verification
     * @return list with commands
     */
    private List<String> getUserListCommand(){
        userListCommand.add("USER_ORDER_COMMAND");
        userListCommand.add("USER_CARD_COMMAND");
        return userListCommand;
    }

    private boolean isContainCommand(List<String> stringList, String command){
        return stringList.stream().anyMatch(command::equalsIgnoreCase);
    }
}