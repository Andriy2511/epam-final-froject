package com.example.finalproject.command.language;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

public class LanguageCommand implements ICommand {
    private static final Logger logger = LogManager.getLogger(LanguageCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        buildURL(request, response);
    }

    private void buildURL(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("URL is " + request.getSession().getAttribute("MyURL"));
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(request.getSession().getAttribute("MyURL"));
        String url = String.valueOf(stringBuilder);
        System.out.println(url);
        request.getSession().removeAttribute("MyURL");
        response.sendRedirect(url);
    }
}
