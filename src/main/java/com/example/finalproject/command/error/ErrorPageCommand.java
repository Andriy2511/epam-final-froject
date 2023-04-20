package com.example.finalproject.command.error;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.SQLException;

/**
 * The ErrorPageCommand class implements the ICommand interface and redirects the user to the error page.
 */
public class ErrorPageCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(ErrorPageCommand.class);

    /**
     * The method redirects user to the error_page.jsp page.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException, ServletException {
        logger.info("Method execute is started");
        logger.debug("Forward to the error/error_page.jsp");
        response.sendRedirect("error/error_page.jsp");
    }
}
