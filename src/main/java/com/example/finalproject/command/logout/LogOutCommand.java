package com.example.finalproject.command.logout;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * LogOutCommand implements ICommand interface and is responsible for log out from personal account
 */
public class LogOutCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(LogOutCommand.class);

    /**
     * This method invalidates the session but saves language parameter. Then the method forwards user to the login.jsp page.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
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
