package com.example.finalproject.command.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AdminProductCommandTest {
    @Test
    public void forwardToChangePageWhenPressButtonChange() {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        String path = "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=change";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
    }

    @Test
    public void deleteGoodsWhenPressButtonDelete() throws ServletException, SQLException, IOException {
        AdminProductCommand adminProductCommand = new AdminProductCommand();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        String path = "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=delete";

        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        adminProductCommand.execute(request, response);
        verify(request, times(1)).getRequestDispatcher(path);
        verify(request, never()).getSession();
        verify(dispatcher).forward(request, response);
    }
}
