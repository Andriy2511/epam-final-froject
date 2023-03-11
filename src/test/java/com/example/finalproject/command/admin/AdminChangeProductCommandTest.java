package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.models.Goods;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.*;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

import static org.mockito.Mockito.*;

public class AdminChangeProductCommandTest {

    private static final IGoodsDAO goodsDAO = DAOFactory.getDaoFactory("MYSQL").getGoodsDAO();
    private static final ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    private static final Goods testGoods = new Goods();
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
    public void changeProductSuccessful() throws IOException, ServletException, SQLException, NamingException, ClassNotFoundException {
        final ICommand servlet = new AdminChangeProductCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        String message = "Goods changed successful";

        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods")));
        when(request.getParameter("name")).thenReturn("testGoods");
        when(request.getParameter("description")).thenReturn("testGoods");
        when(request.getParameter("price")).thenReturn("120");
        when(request.getParameter("category")).thenReturn("testCategory");
        when(request.getContextPath()).thenReturn("/finalProject_war_exploded");
        servlet.execute(request, response);

        verify(response).sendRedirect("/finalProject_war_exploded/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList&NOTIFICATION=" + message);
    }

    @Test
    public void changeProductWithNameWhichAlreadyExist() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        final ICommand servlet = new AdminChangeProductCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        String message = "Goods must contain unique name!";

        when(request.getParameter("goodsId")).thenReturn(String.valueOf(goodsDAO.getGoodsIdByName("testGoods2")));
        when(request.getParameter("name")).thenReturn("testGoods");
        when(request.getParameter("description")).thenReturn("testGoods2");
        when(request.getParameter("price")).thenReturn("140");
        when(request.getParameter("category")).thenReturn("testCategory");
        when(request.getContextPath()).thenReturn("/finalProject_war_exploded");
        servlet.execute(request, response);

        verify(response).sendRedirect("/finalProject_war_exploded/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList&NOTIFICATION=" + message);
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
