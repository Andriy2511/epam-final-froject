package com.example.finalproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "AuthorizationCommandUserFilter")
public class AuthorizationCommandUserFilter implements Filter {
    List<String> userListCommand;
    public void init(FilterConfig config) throws ServletException {
        userListCommand = new ArrayList<>();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String command = req.getParameter("command");
        if(command != null) {
            if (isContainCommand(getUserListCommand(), command)) {
                String userRole = Redirect.getUserRole(req, res);
                if (userRole == null || !userRole.equals("user")) {
                    if (!req.getRequestURI().endsWith("login.jsp")) {
                        Redirect.redirectToLoginPage(req, res, "Log in as user to get access to this page");
                    }
                }
            }
        }
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
