package com.example.finalproject.filter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class Redirect {

    public static String getUserRole(HttpServletRequest request, HttpServletResponse response){
        return (String) request.getSession().getAttribute("userRole");
    }

    public static void redirectToLoginPage(HttpServletRequest request, HttpServletResponse response, String notification) throws ServletException, IOException {
//        response.sendRedirect("/login/login.jsp?NOTIFICATION" + notification);
        RequestDispatcher dispatcher = request.getRequestDispatcher("/login/login.jsp?NOTIFICATION=" + notification);
        //request.setAttribute("NOTIFICATION", notification);
        dispatcher.forward(request, response);
    }
}
