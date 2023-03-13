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

/**
 * The CatalogCommand class implements ICommand interface and responsible for catalog managing.
 */
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

    /**
     * The method receives the "action" parameter from the request and passes control to the corresponding method.
     * If the "action" parameter is null, the method redirects to the catalog_goods/catalog.jsp page.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void showGoodsList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

    /**
     * This method makes an attempt to add product to the card.
     * If user isn't authorized, the method redirects to the login.jsp page with message about it.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void addToCard(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, SQLException {
        logger.info("Method addToCard is started");
        if(isUserRole(request)) {
            int goodsId = Integer.parseInt(request.getParameter("goodsId"));
            cardGoodsList.add(goodsDAO.getGoodsById(goodsId).get(0));
            request.getSession().setAttribute("cardGoodsList", cardGoodsList);
            showGoods(request, response);
        } else {
            logger.debug("Forward to the login/login.jsp, notification: You have to log in to purchase products");
            response.sendRedirect(request.getContextPath() + "/login/login.jsp?NOTIFICATION=locale.NeededRegistration");
        }
    }

    /**
     * This method makes an attempt to purchase product immediately without adding it to the card.
     * If user isn't authorized, the method redirects to the login.jsp page with message about it.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void buyNow(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, SQLException {
        logger.info("Method buyNow is started");
        if(isUserRole(request)) {
            int goodsId = Integer.parseInt(request.getParameter("goodsId"));
            orderDAO.addNewOrder(goodsId, (Integer) request.getSession().getAttribute("id"));
            showGoods(request, response);
        } else {
            logger.debug("Forward to the login/login.jsp, notification: You have to log in to purchase products");
            response.sendRedirect(request.getContextPath() + "/login/login.jsp?NOTIFICATION=locale.NeededRegistration");
        }
    }

    /**
     * The method checks is user role is "user".
     * @param request HttpServletRequest
     * @return if user role is "user" returns - true, otherwise - false.
     */
    private boolean isUserRole(HttpServletRequest request){
        return request.getSession().getAttribute("userRole") != null && request.getSession().getAttribute("userRole").equals("user");
    }

    /**
     * The method forms a list with defined start page and records per page.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        logger.info("Method show Goods is started");
        int countOfGoods;
        countOfGoods = goodsDAO.showCountOfGoods();
        startPage = Pagination.pagination(request, countOfGoods, startPage, recordsPerPage);
        goodsList = sortList(request, response, (startPage-1)*recordsPerPage, recordsPerPage);
        logger.debug("sendGoodsList");
        sendGoodsList(request, response, goodsList);
    }

    /**
     * This method forms a sorted list by parameters: name, price, novelty or default order.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param startPage the record number that will be the first
     * @param recordsPerPage the number of records that will be shown
     * @return sorted list
     */
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

    /**
     * The method writes the list of goods to the session as "goodsList". Then the method sends a redirect to the catalog.jsp page.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param goodsList list of goods to be displayed
     */
    private void sendGoodsList(HttpServletRequest request, HttpServletResponse response, List<Goods> goodsList)
            throws IOException {
        logger.info("Method sendGoodsList is started");
        request.getSession().setAttribute("goodsList", goodsList);
        logger.debug("Send redirect to the catalog_goods/catalog.jsp");
        response.sendRedirect("catalog_goods/catalog.jsp");
    }
}
