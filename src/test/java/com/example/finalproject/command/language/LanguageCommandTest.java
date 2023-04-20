package com.example.finalproject.command.language;

import com.example.finalproject.command.ICommand;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.Test;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import static org.mockito.Mockito.*;

public class LanguageCommandTest {
    @Test
    public void whenCallDoGetThenServletReturnIndexPage() throws ServletException, IOException, SQLException, NamingException, ClassNotFoundException {
        ICommand languageCommand = new LanguageCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpSession session = mock(HttpSession.class);
        String path = "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList";
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("MyURL")).thenReturn(path);

        languageCommand.execute(request, response);

        verify(request.getSession()).getAttribute("MyURL");
        verify(request.getSession(), times(1)).removeAttribute("MyURL");
        verify(response).sendRedirect(path);
    }
}
