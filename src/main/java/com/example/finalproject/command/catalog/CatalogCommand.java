package com.example.finalproject.command.catalog;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.*;
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
import java.util.ArrayList;
import java.util.List;

public class CatalogCommand implements ICommand {
    DAOFactory daoFactory;
    IUserDAO userDAO;
    IRoleDAO roleDAO;
    IGoodsDAO goodsDAO;
    IOrderDAO orderDAO;
    int startPage = 1;
    int recordsPerPage = 8;
    String lastMenu;
    List<Goods> goodsList;
    List<Goods> cardGoodsList;
    String action;
    private static final Logger logger = LogManager.getLogger(CatalogCommand.class);

    public CatalogCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        userDAO = daoFactory.getUserDAO();
        roleDAO = daoFactory.getRoleDAO();
        goodsDAO = daoFactory.getGoodsDAO();
        orderDAO = daoFactory.getOrderDAO();
        cardGoodsList = new ArrayList<>();
        lastMenu = "default";
        action="showGoodsList";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NamingException, ClassNotFoundException {
        showGoodsList(request, response);
    }

    private void showGoodsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException, ClassNotFoundException {
        logger.info("Method showGoodsList is started");
        if(request.getParameter("action") != null)
            action = request.getParameter("action");
        try {
            switch (action){
                case "showGoodsList":
                    showGoods(request, response);
                    break;
                case "addToCard":
                    addToCard(request, response);
                    break;
                case "buyNow":
                    buyNow(request, response);
                    break;
                case "showCard":
                    if(isUserRole(request)){
                        logger.debug("Forward to the user/card_page.jsp, case showCard");
                        RequestDispatcher dispatcher = request.getRequestDispatcher("user/card_page.jsp");
                        dispatcher.forward(request, response);
                        break;
                }
                default:
                    RequestDispatcher dispatcher = request.getRequestDispatcher("catalog_goods/catalog.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void addToCard(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        logger.info("Method addToCard is started");
        if(isUserRole(request)) {
            int goodsId = Integer.parseInt(request.getParameter("goodsId"));
            cardGoodsList.add(goodsDAO.getGoodsById(goodsId).get(0));
            request.getSession().setAttribute("cardGoodsList", cardGoodsList);
            showGoods(request, response);
        } else {
            logger.debug("Forward to the login/login.jsp, notification: You have to log in to purchase products");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp?NOTIFICATION=You have to log in to purchase products");
            dispatcher.forward(request, response);
        }
    }

    private void buyNow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException, NamingException, ClassNotFoundException {
        logger.info("Method buyNow is started");
        if(isUserRole(request)) {
            System.out.println("true");
            int goodsId = Integer.parseInt(request.getParameter("goodsId"));
            orderDAO.addNewOrder(goodsId, (Integer) request.getSession().getAttribute("id"));
            showGoods(request, response);
        } else {
            logger.debug("Forward to the login/login.jsp, notification: You have to log in to purchase products");
            RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp?NOTIFICATION=You have to log in to purchase products");
            dispatcher.forward(request, response);
        }
    }

    private boolean isUserRole(HttpServletRequest request){
        return request.getSession().getAttribute("userRole") != null && request.getSession().getAttribute("userRole").equals("user");
    }

    private void showGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        logger.info("Method show Goods is started");
        int countOfGoods;
        countOfGoods = goodsDAO.showCountOfGoods();
        startPage = Pagination.pagination(request, countOfGoods, startPage, recordsPerPage);
        request.setAttribute("noOfPages", startPage);
        goodsList = sortList(request, response, (startPage-1)*recordsPerPage, recordsPerPage);
        logger.debug("sendGoodsList");
        sendGoodsList(request, response, goodsList);
    }

    private List<Goods> sortList(HttpServletRequest request, HttpServletResponse response, int startPage, int recordsPerPage) throws ServletException, IOException {
        logger.info("Method sortList is started");
        List<Goods> goodsList = new ArrayList<>();
        if (request.getParameter("sort") != null){
            lastMenu = request.getParameter("sort");
        }
        switch (lastMenu){
            case "sortByName":
                goodsList = goodsDAO.sortGoodsByNameGrowth(startPage, recordsPerPage);
                break;
            case "sortByPrice":
                goodsList = goodsDAO.sortGoodsByPriceGrowth(startPage, recordsPerPage);
                break;
            case "sortByNovelty":
                goodsList = goodsDAO.sortGoodsByPublicationDateDecrease(startPage, recordsPerPage);
                break;
            case "default":
                goodsList = goodsDAO.showLimitGoods(startPage, recordsPerPage);
                break;
            default:
                logger.debug("Case default, lastMenu {}, Forward to the catalog_goods/catalog.jsp", lastMenu);
                RequestDispatcher dispatcher = request.getRequestDispatcher("catalog_goods/catalog.jsp");
                dispatcher.forward(request, response);
                break;
        }
        return goodsList;
    }

    private void sendGoodsList(HttpServletRequest request, HttpServletResponse response, List<Goods> goodsList)
            throws IOException {
        logger.info("Method sendGoodsList is started");
        request.getSession().setAttribute("goodsList", goodsList);
        request.setAttribute("currentPage", startPage);
        logger.debug("Send redirect to the catalog_goods/catalog.jsp");
        response.sendRedirect("catalog_goods/catalog.jsp");
    }
}
