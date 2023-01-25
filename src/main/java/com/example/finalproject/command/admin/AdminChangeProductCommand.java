package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.IGoodsDAO;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

public class AdminChangeProductCommand implements ICommand {
    DAOFactory daoFactory;
    IGoodsDAO goodsDAO;
    ICategoryDAO categoryDAO;
    String notification;
    private static final Logger logger = LogManager.getLogger(AdminChangeProductCommand.class);

    public AdminChangeProductCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        goodsDAO = daoFactory.getGoodsDAO();
        categoryDAO = daoFactory.getCategoryDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        changeProduct(request, response);
    }

    private void changeProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Method AddProduct");
        double price = 0;
        int id = 0;
        String photo;
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        System.out.println(request.getParameter("photo"));
        String categoryName = request.getParameter("category");
        try {
            id = Integer.parseInt(request.getParameter("goodsId"));
            price = Double.parseDouble(request.getParameter("price"));
        } catch (NumberFormatException e) {
            logger.error(e);
            e.printStackTrace();
            logger.debug("Forwarding to admin_change_product.jsp after exception");
            response.sendRedirect("admin/admin_change_product.jsp");
        }

        if(price<0) {
            notification = "Price must be higher or equal than 0";
            request.setAttribute("NOTIFICATION", notification);
            RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_change_product.jsp");
            dispatcher.forward(request, response);
        }

        if(request.getPart("photo").getSize() > 0){
            System.out.println(request.getPart("photo"));
            System.out.println(request.getParameter("photo"));
            Part part = request.getPart("photo");
            photo = part.getSubmittedFileName();
            addPhoto(part, photo);
        } else {
            photo = goodsDAO.getGoodsById(id).get(0).getPhoto();
        }

        if(isCategoryExist(categoryName)){
            updateGoods(id, name, description, photo, price, getCategoryId(categoryName));
        } else {
            addCategory(categoryName);
            updateGoods(id, name, description, photo, price, getCategoryId(categoryName));
        }

        request.setAttribute("NOTIFICATION", notification);
        logger.debug("Forward to /FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
        RequestDispatcher dispatcher = request.getRequestDispatcher("/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
        dispatcher.forward(request, response);
    }

    private void addPhoto(Part part, String photo) throws IOException {
        String tempPath = "C:\\Users\\andri\\finalProject\\src\\main\\webapp\\image";
//        String path = request.getServletContext().getRealPath("\\"+ "image"+ File.separator+photo);
        String path = tempPath + File.separator+ photo;
        part.write(path);
    }

    private void updateGoods(int id, String name, String description, String photo, double price, int categoryId) {
        if(goodsDAO.changeGoods(id, name, description, photo, price, categoryId)){
            notification = "Goods changed successful";
        } else {
            notification = "Goods must contain unique name!";
        }
    }
    private void addCategory(String name){
        categoryDAO.addCategory(name);
    }
    private boolean isCategoryExist(String name){
        return categoryDAO.showCategoryByName(name).size() == 1;
    }
    private int getCategoryId(String name){
        return categoryDAO.showCategoryByName(name).get(0).getId();
    }
}
