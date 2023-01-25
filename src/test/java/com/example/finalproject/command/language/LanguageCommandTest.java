package com.example.finalproject.command.language;

import com.example.finalproject.command.user.UserCardCommand;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class LanguageCommandTest {
    //			<a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=en<%request.getSession().setAttribute("mapParam", request.getParameterMap());%><%request.getSession().setAttribute("servletPath", request.getServletPath());%>">ENG</a>
    //			<a href="<%= request.getContextPath()%>/FrontController?command=LANGUAGE_COMMAND&lang=ua<%request.getSession().setAttribute("mapParam", request.getParameterMap()); request.getSession().setAttribute("servletPath", request.getServletPath());%>">UKR</a>
    @Test
    public void whenCallDoGetThenServletReturnIndexPage() throws ServletException, IOException, SQLException {
        LanguageCommand languageCommand = new LanguageCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        String path = "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList";
        request.setAttribute("command", "ADMIN_PRODUCT_CONTROLLER");
        request.setAttribute("action", "showGoodsList");
        request.getSession().setAttribute("mapParam", request.getParameterMap());
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        languageCommand.execute(request, response);
        verify(dispatcher).forward(request, response);
    }
}
