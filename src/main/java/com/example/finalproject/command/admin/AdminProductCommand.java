package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.dao.IRoleDAO;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.Goods;
import com.example.finalproject.pagination.Pagination;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * This class is responsible for managing existing products.
 */
public class AdminProductCommand implements ICommand {
    DAOFactory daoFactory;
    IUserDAO userDAO;
    IRoleDAO roleDAO;
    IGoodsDAO goodsDAO;
    private static final Logger logger = LogManager.getLogger(AddProductCommand.class);
    int startPage = 1;
    int recordsPerPage = 5;
    String listParam;
    List<Goods> goodsList;
    String notification;

    public AdminProductCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        userDAO = daoFactory.getUserDAO();
        roleDAO = daoFactory.getRoleDAO();
        goodsDAO = daoFactory.getGoodsDAO();
        listParam = "";
        notification = "";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        showList(request, response);
    }

    /**
     * This method determines what action the user performed.
     * The method receives the "action" parameter from the request and passes control to appropriate methods.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("The showList changeOrderStatus is started");
        String action = request.getParameter("action");
        try {
            switch (action){
                case "showGoodsList":
                    showGoods(request, response);
                    break;
                case "change":
                    changeGoods(request, response);
                    break;
                case "delete":
                    deleteGoods(request);
                    showGoods(request, response);
                    break;
                default:
                    logger.debug("Forward to admin/admin_goods_list.jsp");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_goods_list.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * This method gets the ID of the selected product and redirects user to the admin_change_product.jsp page.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void changeGoods(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("The method changeGoods is started");
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        Goods goods = goodsDAO.getGoodsById(goodsId).get(0);
        request.getSession().setAttribute("goods", goods);
        logger.debug("Forward to admin/admin_change_product.jsp");
        RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_change_product.jsp");
        dispatcher.forward(request, response);
    }

    /**
     * This method gets goods id from the request and attempts to delete the product from the database.
     * If product can't be deleted the method sends message about it.
     * @param request - HttpServletRequest
     */
    private void deleteGoods(HttpServletRequest request) throws SQLException, ClassNotFoundException {
        logger.info("The method deleteGoods is started");
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        if(!goodsDAO.deleteGoods(goodsId)){
            notification = "This product cannot be deleted because the user has already placed an order for it";
        }
    }

    /**
     * This method obtains the list of goods that should be shown to the user.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showGoods(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        logger.info("The method showGoods is started");
        int countOfGoods;
        countOfGoods = goodsDAO.showCountOfGoods();
        startPage = Pagination.pagination(request, countOfGoods, startPage, recordsPerPage);
        goodsList = goodsDAO.showLimitGoods((startPage-1)*recordsPerPage, recordsPerPage);
        sendGoodsList(request, response, goodsList);
    }

    /**
     * Method set the goods list to the session and send redirect to the admin_goods_list.jsp page
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param goodsList - list of products
     */
    private void sendGoodsList(HttpServletRequest request, HttpServletResponse response, List<Goods> goodsList)
            throws IOException {
        logger.info("The method sendGoodsList is started");
        request.getSession().setAttribute("goodsList", goodsList);
        logger.debug("Forward to admin/admin_goods_list.jsp");
        response.sendRedirect(request.getContextPath() + "/admin/admin_goods_list.jsp" + "?noOfPages=" + startPage + "&NOTIFICATION=" + notification);
    }
}
