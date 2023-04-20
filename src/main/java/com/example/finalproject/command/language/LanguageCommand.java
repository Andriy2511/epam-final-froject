package com.example.finalproject.command.language;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.SQLException;

/**
 * LanguageCommand class implements ICommand interface and is responsible for changing the language
 */
public class LanguageCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(LanguageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        buildURL(request, response);
    }

    /**
     * The method gets the URL from the session and writes it to a variable, then the method removes that attribute from the session.
     * Finally, the method sends a redirect to this url.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void buildURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        logger.info("Method buildURL is started");
        String url = String.valueOf(String.valueOf(request.getSession().getAttribute("MyURL")));
        logger.info("URL: {}", url);
        request.getSession().removeAttribute("MyURL");
        response.sendRedirect(url);
    }
}
