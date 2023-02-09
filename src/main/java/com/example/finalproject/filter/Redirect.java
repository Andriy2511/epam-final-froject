package com.example.finalproject.filter;

import com.example.finalproject.command.admin.AdminChangeProductCommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;

public class Redirect {
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);

    public static String getUserRole(HttpServletRequest request, HttpServletResponse response){
        return (String) request.getSession().getAttribute("userRole");
    }

    public static void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response, String notification) throws ServletException, IOException {
        logger.info("Method redirectToLoginPage is started. Forward to /login/login.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/FrontController?command=LOGOUT_COMMAND&NOTIFICATION=" + notification);
        //response.sendRedirect(request.getContextPath() + "/login/login.jsp?NOTIFICATION=" + notification);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/FrontController?command=LOGIN_CONTROLLER&NOTIFICATION=" + notification);
        dispatcher.forward(request, response);
    }
}
