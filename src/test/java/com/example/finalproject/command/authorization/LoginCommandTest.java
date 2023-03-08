package com.example.finalproject.command.authorization;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.naming.NamingException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.SQLException;

public class LoginCommandTest extends Mockito {
    @Test
    public void authorizationAdminTest() throws IOException, ServletException, SQLException {

        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);

        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("login")).thenReturn("Admin");
        when(request.getParameter("password")).thenReturn("Admin");

        new LoginCommand().execute(request, response);

        verify(request, atLeast(1)).getParameter("login");
        verify(request, atLeast(1)).getParameter("password");

        System.out.println(request.getParameter("NOTIFICATION"));
//        writer.flush(); // it may not have been flushed yet...
//        Assertions.assertTrue(stringWriter.toString().contains("My expected string"));
    }
}
