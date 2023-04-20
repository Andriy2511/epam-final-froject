package com.example.finalproject.command.user;

import com.example.finalproject.dao.*;
import com.example.finalproject.models.Goods;
import com.example.finalproject.models.Order;
import com.example.finalproject.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import static org.mockito.Mockito.*;
public class UserCardCommandTest {
    @Mock
    HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
    @Mock
    HttpServletResponse response;
    @Mock
    HttpSession session;
    @InjectMocks
    UserCardCommand userCardCommand;
    private static final IGoodsDAO goodsDAO = DAOFactory.getDaoFactory("MYSQL").getGoodsDAO();
    private static final ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    private static final IOrderDAO orderDAO = DAOFactory.getDaoFactory("MYSQL").getOrderDAO();
    private static final IUserDAO userDAO = DAOFactory.getDaoFactory("MYSQL").getUserDAO();
    private static final IRoleDAO roleDAO = DAOFactory.getDaoFactory("MYSQL").getRoleDAO();
    Goods testGoods;
    User testUser;

    @BeforeEach
    public void setUpTestEntity() throws SQLException {
        MockitoAnnotations.openMocks(this);
        testGoods = new Goods();
        testUser = new User();
        categoryDAO.addCategory("testCategory");
        int categoryId = categoryDAO.showCategoryByName("testCategory").get(0).getId();
        addTestGoods("testGoods", "testGoods", "testGoods", 100, categoryId);
        setTestUser("testUser", "testUser", "testUser", "testUser", false, roleDAO.getUserId(), "testUser");
    }

    @AfterEach
    public void deleteTestEntity() throws SQLException {
        List<Order> orderList = orderDAO.showOrdersByUser(userDAO.readUserByLogin("testUser").get(0).getId());
        if(orderList != null && orderList.size() > 0)
            orderDAO.deleteOrder(orderList.get(0).getId());
        userDAO.deleteUser(testUser.getLogin());
        goodsDAO.deleteGoods(goodsDAO.getGoodsIdByName(testGoods.getName()));
        categoryDAO.deleteCategory("testCategory");
    }

    @Test
    public void testShowGoodsList() throws IOException, ServletException, SQLException {
        when(request.getParameter("action")).thenReturn("showGoodsList");
        when(request.getSession()).thenReturn(session);
        when(session.getAttribute("cardGoodsList")).thenReturn(anyList());

        userCardCommand.execute(request, response);

        verify(response).sendRedirect("user/card_page.jsp");
    }
    @Test
    public void testConfirmGoods() throws IOException, ServletException, SQLException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));
        when(request.getParameter("action")).thenReturn("confirm");
        when(request.getSession().getAttribute("id")).thenReturn(userDAO.readUserByLogin("testUser").get(0).getId());
        when(session.getAttribute("cardGoodsList")).thenReturn(anyList());

        userCardCommand.execute(request, response);

        verify(response).sendRedirect("user/card_page.jsp");
        verify(session, times(1)).getAttribute("id");
        verify(request, times(2)).getParameter("goodsId");
    }
    @Test
    public void testDeleteGoods() throws IOException, ServletException, SQLException {
        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("id")).thenReturn(userDAO.readUserByLogin("testUser").get(0).getId());
        when(session.getAttribute("cardGoodsList")).thenReturn(anyList());

        userCardCommand.execute(request, response);

        verify(response).sendRedirect("user/card_page.jsp");
        verify(session, never()).getAttribute("id");
        verify(request, times(1)).getParameter("goodsId");
    }

    private void addTestGoods(String name, String description, String photo, double price, int categoryId) throws SQLException {
        testGoods.setName(name);
        testGoods.setDescription(description);
        testGoods.setPhoto(photo);
        testGoods.setPrice(price);
        testGoods.setCategoryId(categoryId);
        goodsDAO.addGoods(testGoods);
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
