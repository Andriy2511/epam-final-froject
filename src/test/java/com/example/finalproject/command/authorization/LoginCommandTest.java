package com.example.finalproject.command.authorization;

import com.example.finalproject.captcha.Captcha;
import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;
import org.mockito.*;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommandTest extends Mockito {
    private static final IUserDAO userDAO = DAOFactory.getDaoFactory("MYSQL").getUserDAO();
    private static final User testUser = new User();

    @BeforeAll
    public static void setTestUser() throws SQLException {
        testUser.setName("testUser");
        testUser.setSurname("testUser");
        testUser.setLogin("testUser");
        testUser.setPassword("testUser");
        testUser.setEmail("testUser");
        userDAO.createUser(testUser);
    }

    @AfterAll
    public static void deleteTestUser() throws SQLException {
        userDAO.deleteUser("testUser");
    }

    @Test
    public void authorizationAdminTest() throws IOException, ServletException, SQLException, NamingException, ClassNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        ICommand servlet = new LoginCommand();

        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("g-recaptcha-response")).thenReturn("true");
        when(request.getParameter("login")).thenReturn("Admin");
        when(request.getParameter("password")).thenReturn("Admin");
        when(request.getContextPath()).thenReturn("/finalProject_war_exploded");

        try (MockedStatic<Captcha> mocked = mockStatic(Captcha.class)) {
            mocked.when(() -> Captcha.isCaptchaPassed(any(HttpServletRequest.class))).thenReturn(true);

            servlet.execute(request, response);

            verify(request, atLeast(1)).getParameter("login");
            verify(request, atLeast(1)).getParameter("password");
            verify(response).sendRedirect("/finalProject_war_exploded/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
        }
    }

    @Test
    public void authorizationUserTest() throws IOException, ServletException, SQLException, NamingException, ClassNotFoundException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpSession httpSession = mock(HttpSession.class);
        ICommand servlet = new LoginCommand();

        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("g-recaptcha-response")).thenReturn("true");
        when(request.getParameter("login")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getContextPath()).thenReturn("/finalProject_war_exploded");

        try (MockedStatic<Captcha> mocked = mockStatic(Captcha.class)) {
            mocked.when(() -> Captcha.isCaptchaPassed(any(HttpServletRequest.class))).thenReturn(true);

            servlet.execute(request, response);

            verify(request, atLeast(1)).getParameter("login");
            verify(request, atLeast(1)).getParameter("password");
            verify(response).sendRedirect( "/finalProject_war_exploded/FrontController?command=CATALOG_COMMAND&action=showGoodsList");
        }
    }
}
