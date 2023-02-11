package com.example.finalproject.command.logout;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.admin.AddProductCommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class LogOutCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        logger.info("Class LogOutCommand. Method execute is started");
        String language = (String) request.getSession().getAttribute("lang");
        request.getSession().invalidate();
        request.getSession().setAttribute("lang", language);
        logger.debug("Redirect to the {}/login/login.jsp", request.getContextPath());
        request.getRequestDispatcher("login/login.jsp").forward(request, response);
    }
}
