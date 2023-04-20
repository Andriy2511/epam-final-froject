package com.example.finalproject.filter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;

/**
 * The Redirect class is responsible for redirecting to the login.jsp page
 */
public class Redirect {
    private static final Logger logger = LogManager.getLogger(Redirect.class);

    /**
     * Gets user's role from the session
     * @param request HttpServletRequest
     * @return user's role
     */
    public static String getUserRole(HttpServletRequest request){
        return (String) request.getSession().getAttribute("userRole");
    }

    /**
     * Redirects to a login page with a reason message
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param notification notification about the reason for redirecting the user to the login page
     */
    public static void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response, String notification) throws ServletException, IOException {
        logger.info("Method redirectToLoginPage is started. Forward to /login/login.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/FrontController?command=LOGOUT_COMMAND&NOTIFICATION=" + notification);
        dispatcher.forward(request, response);
    }
}
