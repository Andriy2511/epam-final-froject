package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.*;
import com.example.finalproject.models.Goods;
import com.example.finalproject.models.Order;
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

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AdminOrderCommandTest {
    private static final IGoodsDAO goodsDAO = DAOFactory.getDaoFactory("MYSQL").getGoodsDAO();
    private static final ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    private static final IOrderDAO orderDAO = DAOFactory.getDaoFactory("MYSQL").getOrderDAO();
    private static final IOrderStatusDAO orderStatusDAO = DAOFactory.getDaoFactory("MYSQL").getOrderStatusDAO();
    private static final IUserDAO userDAO = DAOFactory.getDaoFactory("MYSQL").getUserDAO();
    private static final IRoleDAO roleDAO = DAOFactory.getDaoFactory("MYSQL").getRoleDAO();
    private Goods testGoods;
    private User testUser;
    private Order testOrder;

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private HttpServletRequest request  = mock(HttpServletRequest.class);
    @Mock
    private HttpServletResponse response = mock(HttpServletResponse.class);
    @Mock
    private RequestDispatcher dispatcher = mock(RequestDispatcher.class);

    @BeforeEach
    public void setTestParameters() throws SQLException, NamingException, ClassNotFoundException {
        categoryDAO.addCategory("testCategory");
        testGoods = new Goods();
        int categoryId = categoryDAO.showCategoryByName("testCategory").get(0).getId();
        addTestGoods("testGoods", "testGoods", "testGoods", 100, categoryId);

        testUser = new User();
        roleDAO.addNewRole("user");
        int userRoleId = userDAO.getRoleIdUser();
        setTestUser("testUser", "testUser", "testUser", "testUser", false, userRoleId, "testUser");

        setOrderStatus();

        testOrder = new Order();
        addTestOrder(goodsDAO.getGoodsIdByName("testGoods"), userDAO.getUserId(testUser), orderStatusDAO.getOrderStatusIdRegistered());
    }

    @AfterEach
    public void deleteTestProduct() throws SQLException {
        orderDAO.deleteOrder(testOrder.getId());
        userDAO.deleteUser("testUser");
        goodsDAO.deleteGoods(goodsDAO.getGoodsIdByName("testGoods"));
        categoryDAO.deleteCategory("testCategory");
    }

    @Test
    public void testConfirmOrder() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        int orderId = testOrder.getId();
        ICommand servlet = new AdminOrderCommand();

        when(request.getParameter("action")).thenReturn("paid");
        when(request.getParameter("orderId")).thenReturn(String.valueOf(orderId));
        String path = "admin/admin_order_list.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        Assertions.assertEquals(orderDAO.selectOrderIdByName("paid"), orderDAO.getOrderById(orderId).get(0).getOrderStatusId());
    }
    @Test
    public void testCancelOrder() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        int orderId = testOrder.getId();
        ICommand servlet = new AdminOrderCommand();
        when(request.getParameter("action")).thenReturn("canceled");
        when(request.getParameter("orderId")).thenReturn(String.valueOf(orderId));
        String path = "admin/admin_order_list.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        Assertions.assertEquals(orderDAO.selectOrderIdByName("canceled"), orderDAO.getOrderById(orderId).get(0).getOrderStatusId());
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

    private void setOrderStatus() throws SQLException {
        orderStatusDAO.addNewOrderStatus("registered");
        orderStatusDAO.addNewOrderStatus("paid");
        orderStatusDAO.addNewOrderStatus("canceled");
    }

    private void addTestOrder(int goodsId, int userId, int orderStatusId) throws SQLException, NamingException, ClassNotFoundException {
        testOrder.setGoodsId(goodsId);
        testOrder.setUserId(userId);
        testOrder.setOrderStatusId(orderStatusId);
        testOrder.setId(orderDAO.addNewOrder(goodsId, userId));
    }
}
