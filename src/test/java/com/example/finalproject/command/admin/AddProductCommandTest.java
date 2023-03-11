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
import org.junit.jupiter.api.*;

import java.io.IOException;
import java.sql.SQLException;
import static org.mockito.Mockito.*;
import javax.naming.NamingException;

public class AddProductCommandTest {
    private static final IGoodsDAO goodsDAO = DAOFactory.getDaoFactory("MYSQL").getGoodsDAO();
    private static final ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    private static final Goods testGoods = new Goods();

    @BeforeEach
    public void setTestGoods() throws SQLException {
        categoryDAO.addCategory("testCategory");
        int categoryId = categoryDAO.showCategoryByName("testCategory").get(0).getId();
        addTestGoods("testGoods", "testGoods", "testGoods", 100, categoryId);
    }

    @AfterEach
    public void deleteTestProduct() throws SQLException {
        goodsDAO.deleteGoods(goodsDAO.getGoodsIdByName("testGoods"));
        categoryDAO.deleteCategory("testCategory");
    }

    @Test
    public void forwardToAdminAddProductPageWhenPriceLowerThanZero() throws ServletException, SQLException, NamingException, IOException, ClassNotFoundException {
        final ICommand servlet = new AddProductCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);
        final RequestDispatcher dispatcher = mock(RequestDispatcher.class);

        String path = "admin/admin_add_product.jsp";
        when(request.getParameter("price")).thenReturn("-1");
        when(request.getParameter("category")).thenReturn("test");
        when(request.getRequestDispatcher(path)).thenReturn(dispatcher);

        servlet.execute(request, response);
        verify(dispatcher).forward(request, response);
        verify(request, times(1)).getRequestDispatcher(path);
    }

    @Test
    public void testAddProductWhichAlreadyExist() throws IOException, ServletException, SQLException, NamingException, ClassNotFoundException {
        final ICommand servlet = new AddProductCommand();
        final HttpServletRequest request = mock(HttpServletRequest.class, RETURNS_DEEP_STUBS);
        final HttpServletResponse response = mock(HttpServletResponse.class);

        String errorMessage= "Goods must contain unique name!";

        when(request.getParameter("name")).thenReturn("testGoods");
        when(request.getParameter("description")).thenReturn("testGoods");
        when(request.getParameter("price")).thenReturn("100");
        when(request.getParameter("category")).thenReturn("testCategory");

        servlet.execute(request, response);
        verify(response).sendRedirect("admin/admin_add_product.jsp?NOTIFICATION=" + errorMessage);
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
