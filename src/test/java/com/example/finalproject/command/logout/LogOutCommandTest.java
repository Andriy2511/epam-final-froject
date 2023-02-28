package com.example.finalproject.command.logout;

import com.example.finalproject.command.user.UserCardCommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;


public class LogOutCommandTest {
    //Delete
    @Test
    public void whenCallDoGetThenServletReturnIndexPage() throws ServletException, IOException, SQLException {
        final UserCardCommand servlet = new UserCardCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        String path = request.getContextPath() + "/FrontController?command=CATALOG_COMMAND&action=showCard";
        System.out.println(path);

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher); //var... args => OngoingStubbing<T> thenReturn(T value, T... values);

        servlet.execute(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }

    @Test
    public void whenCallLogOutCommandReturnLoginPage() throws ServletException, SQLException, IOException {
        final LogOutCommand servlet = new LogOutCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        String path = "login/login.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        verify(request, atLeast(2)).getSession();
        verify(dispatcher).forward(request, response);
    }
}
