package com.example.finalproject.command.error;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.verify;

public class ErrorPageCommandTest {
    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @InjectMocks
    private ErrorPageCommand errorPageCommand;

    @Test
    public void testExecute() throws SQLException, IOException, ServletException {
        MockitoAnnotations.openMocks(this);
        errorPageCommand.execute(request, response);

        verify(response).sendRedirect("error/error_page.jsp");
    }
}
