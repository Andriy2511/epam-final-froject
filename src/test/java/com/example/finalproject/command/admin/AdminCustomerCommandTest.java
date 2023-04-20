package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.*;
import com.example.finalproject.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Answers;
import org.mockito.Mock;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;

public class AdminCustomerCommandTest {
    private static final IUserDAO userDAO = DAOFactory.getDaoFactory("MYSQL").getUserDAO();
    private static final IRoleDAO roleDAO = DAOFactory.getDaoFactory("MYSQL").getRoleDAO();
    User testUser;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request  = mock(HttpServletRequest.class);
    @Mock
    private HttpServletResponse response = mock(HttpServletResponse.class);
    @Mock
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    @BeforeEach
    public void setUpTestUser() throws SQLException {
        roleDAO.addNewRole("user");
        int userRoleId = userDAO.getRoleIdUser();
        testUser = new User();
        setTestUser("testUser", "testUser", "testUser", "testUser", false, userRoleId, "testUser");
    }


    @AfterEach
    public void deleteTestEntity() throws SQLException {
        userDAO.deleteUser("testUser");
    }

    @Test
    public void testBlockUser() throws NamingException, ClassNotFoundException, ServletException, SQLException, IOException {
        int userId = userDAO.getUserId(testUser);
        ICommand servlet = new AdminCustomerCommand();
        when(request.getParameter("action")).thenReturn("block");
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        String path = "admin/admin_customer_list.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        Assertions.assertTrue(userDAO.getUserById(userId).get(0).getStatusBlocked());

    }
    @Test
    public void testUnblockUser() throws NamingException, ClassNotFoundException, ServletException, SQLException, IOException {
        int userId = userDAO.getUserId(testUser);
        ICommand servlet = new AdminCustomerCommand();
        when(request.getParameter("action")).thenReturn("unblock");
        when(request.getParameter("userId")).thenReturn(String.valueOf(userId));
        String path = "admin/admin_customer_list.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        userDAO.blockUser(userId);
        Assertions.assertTrue(userDAO.getUserById(userId).get(0).getStatusBlocked());
        servlet.execute(request, response);
        Assertions.assertFalse(userDAO.getUserById(userId).get(0).getStatusBlocked());
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
