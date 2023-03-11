package com.example.finalproject.command.user;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.*;
import com.example.finalproject.models.Goods;
import com.example.finalproject.models.Order;
import com.example.finalproject.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class UserOrderCommandTest {
    private static final IUserDAO userDAO = DAOFactory.getDaoFactory("MYSQL").getUserDAO();
    private static final IRoleDAO roleDAO = DAOFactory.getDaoFactory("MYSQL").getRoleDAO();
    Goods testGoods;
    User testUser;
    Order testOrder;

    @BeforeEach
    public void setUpTestEntity() throws SQLException {
        MockitoAnnotations.openMocks(this);
        testGoods = new Goods();
        testUser = new User();
        testOrder = new Order();
        setTestUser("testUser", "testUser", "testUser", "testUser", false, roleDAO.getUserId(), "testUser");
    }

    @AfterEach
    public void deleteTestEntity() throws SQLException {
        userDAO.deleteUser(testUser.getLogin());
    }
    @Test
    public void testShowList() throws SQLException, ServletException, IOException, NamingException, ClassNotFoundException {
        ICommand servlet = new UserOrderCommand();
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpSession session = mock(HttpSession.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("id")).thenReturn(userDAO.readUserByLogin("testUser").get(0).getId());
        when(request.getRequestDispatcher("user/user_order_list.jsp")).thenReturn(dispatcher);

        servlet.execute(request, response);

        verify(request, times(1)).setAttribute(eq("noOfPages"), anyInt());
        verify(session, times(1)).setAttribute(eq("orderList"), anyList());
        verify(request, times(1)).setAttribute(eq("noOfPages"), anyInt());
        verify(dispatcher).forward(request, response);
    }

    private void setTestUser(String name, String surname, String login, String password, boolean statusBlocked, int roleId, String email) throws SQLException {
        testUser.setName(name);
        testUser.setSurname(surname);
        testUser.setLogin(login);
        testUser.setPassword(password);
        testUser.setStatusBlocked(statusBlocked);
        testUser.setRoleId(roleId);
        testUser.setEmail(email);
        userDAO.createUser(testUser);
    }
}
