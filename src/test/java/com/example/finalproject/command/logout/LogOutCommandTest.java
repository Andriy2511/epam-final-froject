package com.example.finalproject.command.logout;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class LogOutCommandTest {
    @Test
    public void whenCallLogOutCommandReturnLoginPage() throws ServletException, SQLException, IOException, NamingException, ClassNotFoundException {
        final ICommand servlet = new LogOutCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        String path = "login/login.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        verify(request, times(1)).getRequestDispatcher(path);
        //The session is called the first time when the invalidate method is called and the second time when sets language parameter
        verify(request, atLeast(2)).getSession();
        verify(dispatcher).forward(request, response);
    }
}
