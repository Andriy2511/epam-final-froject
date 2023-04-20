package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.path.PathBuilder;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.SQLException;

/**
 * This class response for changing product
 */
public class AdminChangeProductCommand implements ICommand {
    private IGoodsDAO goodsDAO;
    private ICategoryDAO categoryDAO;
    String notification;
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);

    public AdminChangeProductCommand(){
        DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
        goodsDAO = daoFactory.getGoodsDAO();
        categoryDAO = daoFactory.getCategoryDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        changeProduct(request, response);
    }

    /**
     * This method get parameters from the request and trying to change product.
     * If user enters incorrect or duplicate values the method sends a message.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void changeProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        logger.info("Method changeProduct");
        double price = 0;
        int id = 0;
        String photo;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String categoryName = request.getParameter("category");
        if(categoryName == null)
            categoryName = "Other category";
        try {
            id = Integer.parseInt(request.getParameter("goodsId"));
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            logger.error(e);
            e.printStackTrace();
            logger.debug("Forwarding to admin_change_product.jsp after exception");
            response.sendRedirect("admin/admin_change_product.jsp");
        }

        if(request.getPart("photo").getSize() > 0){
            Part part = request.getPart("photo");
            photo = part.getSubmittedFileName();
            addPhoto(part, photo, request);
        } else {
            photo = goodsDAO.getGoodsById(id).get(0).getPhoto();
        }

        if(price >= 0) {
            if (isCategoryExist(categoryName)) {
                updateGoods(id, name, description, photo, price, getCategoryId(categoryName));
            } else {
                addCategory(categoryName);
                updateGoods(id, name, description, photo, price, getCategoryId(categoryName));
            }
            logger.debug("Forward to /FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
            logger.info("Notification is {}", notification);
            response.sendRedirect(request.getContextPath() + "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList&NOTIFICATION=" + notification);
        } else {
            notification = "locale.MessageNegativePrice";
            logger.debug("Forward to the admin/admin_change_product.jsp");
            logger.info("Notification is {}, \nprice = {}", notification, price);
            request.setAttribute("NOTIFICATION", notification);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_change_product.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * This method write photo path to the variable part
     * @param part - photo Part
     * @param photo - photo name
     * @param request - HttpServletRequest
     */
    private void addPhoto(Part part, String photo, HttpServletRequest request) throws IOException {
        String path = PathBuilder.buildImagePath(request, photo);
        part.write(path);
    }

    /**
     * This method attempts to update the product and set the appropriate message to the variable notification about success or failure
     * @param id - product id
     * @param name - product name
     * @param description - product description
     * @param photo - product photo
     * @param price - product price
     * @param categoryId - id of product category
     */
    private void updateGoods(int id, String name, String description, String photo, double price, int categoryId) throws SQLException {
        if(name!=null && name.length() > 3 && goodsDAO.changeGoods(id, name, description, photo, price, categoryId)){
            notification = "locale.MessageGoodsChangedSuccessful";
        } else {
            notification = "locale.MessageGoodsAddedUnsuccessful";
        }
    }

    /**
     * This method calls addCategory method from the CategoryDAO class, which adds the category to the database.
     * @param name - category name
     */
    private void addCategory(String name) throws SQLException {
        categoryDAO.addCategory(name);
    }

    /**
     * This method checks whether category exist or not.
     * @param name - category name
     * @return if the value is true, the category exist, otherwise the category absent.
     */
    private boolean isCategoryExist(String name){
        return categoryDAO.showCategoryByName(name).size() == 1;
    }

    /**
     * This method gets category id by name.
     * @param name - name of the product
     * @return - id of the category
     */
    private int getCategoryId(String name){
        return categoryDAO.showCategoryByName(name).get(0).getId();
    }
}
