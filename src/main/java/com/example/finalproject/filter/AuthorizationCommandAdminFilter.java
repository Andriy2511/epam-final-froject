package com.example.finalproject.filter;

import jakarta.servlet.*;
import jakarta.servlet.annotation.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebFilter(filterName = "AuthorizationCommandAdminFilter")
public class AuthorizationCommandAdminFilter implements Filter {
    List<String> adminListCommand = new ArrayList<>();
    public void init(FilterConfig config) throws ServletException {
        adminListCommand = new ArrayList<>();
    }

    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws ServletException, IOException {
        final HttpServletRequest req = (HttpServletRequest) request;
        final HttpServletResponse res = (HttpServletResponse) response;
        String command = req.getParameter("command");
        if(command != null) {
            if (isContainCommand(getAdminListCommand(), command)) {
                String userRole = Redirect.getUserRole(req, res);
                if (userRole == null || !userRole.equals("admin")) {
                    if (!req.getRequestURI().endsWith("login.jsp")) {
                        Redirect.redirectToLoginPage(req, res, "Log in as admin to get access to this page");
                    }
                }
            }
        }
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
