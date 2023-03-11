package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.models.Goods;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.*;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class AdminProductCommandTest {
    private static final IGoodsDAO goodsDAO = DAOFactory.getDaoFactory("MYSQL").getGoodsDAO();
    private static final ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    private static final Goods testGoods = new Goods();
    ICommand servlet = new AdminProductCommand();
    @BeforeEach
    public void setTestProducts() throws SQLException, NamingException, ClassNotFoundException {
        categoryDAO.addCategory("testCategory");
        int categoryId = categoryDAO.showCategoryByName("testCategory").get(0).getId();
        addTestGoods("testGoods", "testGoods", "testGoods", 100, categoryId);
        addTestGoods("testGoods2", "testGoods2", "testGoods2", 120, categoryId);
    }

    @AfterEach
    public void deleteTestEntity() throws SQLException, NamingException, ClassNotFoundException {
        goodsDAO.deleteGoods(goodsDAO.getGoodsIdByName("testGoods"));
        goodsDAO.deleteGoods(goodsDAO.getGoodsIdByName("testGoods2"));
        categoryDAO.deleteCategory("testCategory");
    }
    @Test
    public void testForwardToChangePage() throws ServletException, IOException, SQLException, NamingException, ClassNotFoundException {
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
        final HttpSession httpSession = mock(HttpSession.class);

        String path = "admin/admin_change_product.jsp";
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(httpSession);
        when(request.getParameter("action")).thenReturn("change");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));

        servlet.execute(request, response);

        verify(request, times(1)).getRequestDispatcher(path);
        verify(dispatcher).forward(request, response);

    }

    @Test
    public void testDeleteGoods() throws ServletException, SQLException, IOException, NamingException, ClassNotFoundException {
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final HttpServletRequest request = mock(HttpServletRequest.class);
        final HttpSession httpSession = mock(HttpSession.class);

        when(request.getParameter("action")).thenReturn("delete");
        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods2")));
        when(request.getSession()).thenReturn(httpSession);
        servlet.execute(request, response);

        assertEquals(0, goodsDAO.getGoodsIdByName("testGoods2"));
    }

    private void addTestGoods(String name, String description, String photo, double price, int categoryId) throws SQLException {
        testGoods.setName(name);
        testGoods.setDescription(description);
        testGoods.setPhoto(photo);
        testGoods.setPrice(price);
        testGoods.setCategoryId(categoryId);
        goodsDAO.addGoods(testGoods);
    }
}
