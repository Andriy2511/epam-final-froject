package com.example.finalproject.command.catalog;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import static org.mockito.Mockito.*;

public class CatalogCommandTest {

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private RequestDispatcher dispatcher;

    @Mock
    HttpSession session;

    @InjectMocks
    private CatalogCommand catalogCommand;
    private static final IGoodsDAO goodsDAO = DAOFactory.getDaoFactory("MYSQL").getGoodsDAO();
    private static final ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    private static final IOrderDAO orderDAO = DAOFactory.getDaoFactory("MYSQL").getOrderDAO();
    private static final IUserDAO userDAO = DAOFactory.getDaoFactory("MYSQL").getUserDAO();
    private static final IRoleDAO roleDAO = DAOFactory.getDaoFactory("MYSQL").getRoleDAO();
    Goods testGoods;
    User testUser;

    @BeforeEach
    public void setUp() throws SQLException {
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
    public void testShowGoodsList() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        when(request.getParameter("action")).thenReturn("showGoodsList");
        when(request.getSession()).thenReturn(session);
        String path = "catalog_goods/catalog.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        catalogCommand.execute(request, response);

        verify(request, times(2)).getSession();
        verify(request.getSession(), times(1)).setAttribute(eq("goodsList"), anyList());
        verify(response).sendRedirect("catalog_goods/catalog.jsp");
    }

    @Test
    public void testAddToCardSuccessfully() throws IOException, ServletException, SQLException, NamingException, ClassNotFoundException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("action")).thenReturn("addToCard");
        when(request.getSession().getAttribute("userRole")).thenReturn("user");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));

        catalogCommand.execute(request, response);

        verify(session, times(1)).setAttribute(eq("cardGoodsList"), anyList());
        verify(session, times(1)).setAttribute(eq("goodsList"), anyList());
        verify(response).sendRedirect("catalog_goods/catalog.jsp");
    }

    @Test
    public void testAddToCardUnsuccessfully() throws IOException, ServletException, SQLException, NamingException, ClassNotFoundException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("action")).thenReturn("addToCard");
        when(request.getSession().getAttribute("userRole")).thenReturn("admin");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));
        when(request.getContextPath()).thenReturn("testPath");

        catalogCommand.execute(request, response);

        verify(session, atLeast(1)).getAttribute("userRole");
        verify(session, never()).setAttribute(eq("cardGoodsList"), anyList());
        verify(session, never()).setAttribute(eq("goodsList"), anyList());
        verify(response).sendRedirect(request.getContextPath()+"/login/login.jsp?NOTIFICATION=locale.NeededRegistration");
    }
    @Test
    public void testBuyNowSuccessfully() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        HttpServletResponse response = mock(HttpServletResponse.class);
        HttpServletRequest request = mock(HttpServletRequest.class);
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("action")).thenReturn("buyNow");
        when(request.getSession().getAttribute("id")).thenReturn(userDAO.readUserByLogin("testUser").get(0).getId());
        when(request.getSession().getAttribute("userRole")).thenReturn("user");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));

        catalogCommand.execute(request, response);

        verify(session, atLeast(1)).getAttribute("userRole");
        verify(session, never()).setAttribute(eq("cardGoodsList"), anyList());
        verify(session, times(1)).setAttribute(eq("goodsList"), anyList());
        verify(response).sendRedirect("catalog_goods/catalog.jsp");

    }
    @Test
    public void testBuyNowUnsuccessfully() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        when(request.getSession()).thenReturn(session);
        when(request.getParameter("action")).thenReturn("buyNow");
        when(request.getSession().getAttribute("id")).thenReturn(userDAO.readUserByLogin("testUser").get(0).getId());
        when(request.getSession().getAttribute("userRole")).thenReturn("admin");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));
        when(request.getContextPath()).thenReturn("testPath");

        catalogCommand.execute(request, response);

        verify(session, atLeast(1)).getAttribute("userRole");
        verify(session, never()).setAttribute(eq("cardGoodsList"), anyList());
        verify(session, never()).setAttribute(eq("goodsList"), anyList());
        verify(response).sendRedirect(request.getContextPath()+"/login/login.jsp?NOTIFICATION=locale.NeededRegistration");
    }
    @Test
    public void testShowCard() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        String path = "user/card_page.jsp";
        when(request.getParameter("action")).thenReturn("showCard");
        when(request.getSession()).thenReturn(session);
        when(request.getSession().getAttribute("userRole")).thenReturn("user");
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        catalogCommand.execute(request, response);

        verify(session, atLeast(1)).getAttribute("userRole");
        verify(dispatcher).forward(request, response);
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
