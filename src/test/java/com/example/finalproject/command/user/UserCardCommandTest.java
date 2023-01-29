package com.example.finalproject.command.user;

import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class UserCardCommandTest {
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
}
