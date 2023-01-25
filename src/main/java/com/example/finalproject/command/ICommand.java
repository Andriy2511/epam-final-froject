package com.example.finalproject.command;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public interface ICommand {
    void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NamingException, ClassNotFoundException;
}
