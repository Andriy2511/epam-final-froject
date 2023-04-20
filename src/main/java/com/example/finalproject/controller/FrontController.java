package com.example.finalproject.controller;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.factory.CommandFactory;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * The FrontController class handles the request
 */
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 10, // 10 MB
        maxFileSize = 1024 * 1024 * 1000, // 1 GB
        maxRequestSize = 1024 * 1024 * 1000)   	// 1 GB
@WebServlet(name = "FrontController", value = "/FrontController")
public class FrontController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(FrontController.class);
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        handleRequest(request, response);
    }

    /**
     * Gets a command from CommendEnum and passes control to the class that calls the "execute" method
     * @param req HttpServletRequest
     * @param resp HttpServletResponse
     */
    private void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        ICommand iCommand = CommandFactory.getCommand(req);
        try {
            iCommand.execute(req, resp);
        } catch (SQLException | ServletException | IOException | NamingException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}