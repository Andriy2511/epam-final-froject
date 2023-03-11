package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.models.Goods;
import com.example.finalproject.path.PathBuilder;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.*;
import java.sql.SQLException;

/**
 * The AddProductCommand class adds product to the database.
 * @author Andrii Sirko
 */
public class AddProductCommand implements ICommand {
    DAOFactory daoFactory;
    IGoodsDAO goodsDAO;
    ICategoryDAO categoryDAO;
    String notification;
    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);

    public AddProductCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        goodsDAO = daoFactory.getGoodsDAO();
        categoryDAO = daoFactory.getCategoryDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, ClassNotFoundException {
        addProduct(request, response);
    }

    /**
     * This method gets parameters from the request and trying to add photo to the database.
     * Attributes which method gets from the request: name, description, price, category
     * and Part photo. If Part photo has value is null the method sets default value "without photo".
     * If user enters incorrect values the method sends an appropriate message.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */

    private void addProduct(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        logger.info("The showList addProduct is started");
        double price = 0;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String photo = "without photo";
        String categoryName = request.getParameter("category");
        try {
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            logger.error(e);
            e.printStackTrace();
            response.sendRedirect("admin/admin_add_product.jsp");
        }

        if(request.getPart("photo").getSize() > 0) {
            Part part = request.getPart("photo");
            photo = part.getSubmittedFileName();
            addPhoto(part, photo, request);
        }

        if(price>=0){
            logger.info("category name is {}", categoryName);
            if(isCategoryExist(categoryName)){
                addGoods(name, description, photo, price, categoryName);
            } else {
                addCategory(categoryName);
                addGoods(name, description, photo, price, categoryName);
            }
            logger.debug("Redirect to admin_add_product.jsp");
            response.sendRedirect("admin/admin_add_product.jsp?NOTIFICATION=" + notification);
        } else {
            notification = "Price must be higher or equal than 0";
            request.setAttribute("NOTIFICATION", notification);
            logger.debug("Forward to admin_add_product.jsp, notification {}", notification);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_add_product.jsp");
            dispatcher.forward(request, response);
        }
    }

    /**
     * This method write photo path to the variable part.
     * @param part - photo Part
     * @param photo - name of the photo
     * @param request - HttpServletRequest
     */
    private void addPhoto(Part part, String photo, HttpServletRequest request) throws IOException {
        String path = PathBuilder.buildImagePath(request, photo);
        part.write(path);
    }

    /**
     * This method calls method addGoods from GoodsDAO class which add product to the database.
     * For variable notification set the corresponding message to indicate whether the product was added successfully or not.
     * @param name - product name
     * @param description - product description
     * @param photo - name of the photo
     * @param price - product price
     * @param categoryName - product category
     */
    private void addGoods(String name, String description, String photo, double price, String categoryName) throws SQLException{
        Goods goods = new Goods(name, description, photo, price, getCategoryId(categoryName));
        if(goodsDAO.addGoods(goods)){
            notification = "Goods added successful";
        } else {
            notification = "Goods must contain unique name!";
        }
    }

    /**
     * This method checks whether category exist or not. If size of the list equal to 1 - category exist, else - absent.
     * @param name - name of the product
     * @return A boolean value whether category exist or not.
     */
    private boolean isCategoryExist(String name){
        return categoryDAO.showCategoryByName(name).size() == 1;
    }

    /**
     * This method calls addCategory method form CategoryDAO which adds product to the database.
     * @param name - name of the product
     */
    private void addCategory(String name) throws SQLException {
        categoryDAO.addCategory(name);
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
