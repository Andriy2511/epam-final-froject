package com.example.finalproject.command.authorization;

import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class RegistrationCommandTest {

    @Spy
    private DAOFactory daoFactory;

    @Mock
    private IUserDAO userDao;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    private RegistrationCommand servlet;
    private User testUser;

    @BeforeEach
    public void setUp() {
        testUser = new User();
        MockitoAnnotations.openMocks(this);
        servlet = new RegistrationCommand();
        daoFactory.getUserDAO();
    }

    @AfterEach
    public void deleteTestEntity() throws SQLException {
        userDao.deleteUser("testUser");
    }

    @Test
    public void testRegistrationSuccess() throws ServletException, IOException, SQLException {
        when(request.getParameter("name")).thenReturn("testUser");
        when(request.getParameter("surname")).thenReturn("testUser");
        when(request.getParameter("login")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getParameter("email")).thenReturn("testUser");
        when(userDao.createUser(any(User.class))).thenReturn(true);

        servlet.execute(request, response);

        verify(response, times(1)).sendRedirect(eq("login/login.jsp?NOTIFICATION=locale.RegistrationSuccessful"));
    }

    @Test
    public void testRegistrationFailure() throws ServletException, IOException, SQLException {
        int userRoleId = userDao.getRoleIdUser();
        setTestUser("testUser", "testUser", "testUser", "testUser", false, userRoleId, "testUser");

        when(request.getParameter("name")).thenReturn("testUser");
        when(request.getParameter("surname")).thenReturn("testUser");
        when(request.getParameter("login")).thenReturn("testUser");
        when(request.getParameter("password")).thenReturn("testUser");
        when(request.getParameter("email")).thenReturn("testUser");
        when(userDao.createUser(any(User.class))).thenReturn(false);

        servlet.execute(request, response);

        verify(response, times(1)).sendRedirect(eq("register/register.jsp?NOTIFICATION=locale.RegistrationUnsuccessful"));
    }

    private void setTestUser(String name, String surname, String login, String password, boolean statusBlocked, int roleId, String email) throws SQLException {
        testUser.setName(name);
        testUser.setSurname(surname);
        testUser.setLogin(login);
        testUser.setPassword(password);
        testUser.setStatusBlocked(statusBlocked);
        testUser.setRoleId(roleId);
        testUser.setEmail(email);
        userDao.createUser(testUser);
    }
}
