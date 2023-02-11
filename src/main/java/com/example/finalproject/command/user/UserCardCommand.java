package com.example.finalproject.command.user;

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

public class UserCardCommand implements ICommand {

    DAOFactory daoFactory;
    IUserDAO userDAO;
    IRoleDAO roleDAO;
    IGoodsDAO goodsDAO;
    IOrderDAO orderDAO;
    String listParam;
    private static final Logger logger = LogManager.getLogger(UserCardCommand.class);
    List<Goods> cardGoodsList;

    public UserCardCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        userDAO = daoFactory.getUserDAO();
        roleDAO = daoFactory.getRoleDAO();
        goodsDAO = daoFactory.getGoodsDAO();
        orderDAO = daoFactory.getOrderDAO();
        cardGoodsList = new ArrayList<>();
        listParam = "";
    }
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        showList(request, response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Method showList is started");
        String action = request.getParameter("action");
        try {
            switch (action){
                case "showGoodsList":
                    showGoods(request, response);
                    break;
                case "confirm":
                    confirmGoods(request, response);
                    deleteGoods(request, response);
                    showGoods(request, response);
                    break;
                case "delete":
                    deleteGoods(request, response);
                    showGoods(request, response);
                    break;
                default:
                    logger.debug("Case default, action {}, redirect to the user/card_page.jsp", action);
                    RequestDispatcher dispatcher = request.getRequestDispatcher("user/card_page.jsp");
                    dispatcher.forward(request, response);
                    break;
            }
        } catch (SQLException | NamingException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    private void confirmGoods(HttpServletRequest request, HttpServletResponse response) throws SQLException, NamingException, ClassNotFoundException {
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        Integer id = (Integer) request.getSession().getAttribute("id");
        orderDAO.addNewOrder(goodsId, id);
    }

    @SuppressWarnings("unchecked")
    private void deleteGoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        cardGoodsList = (List<Goods>) request.getSession().getAttribute("cardGoodsList");
        cardGoodsList.remove(getGoodsById(cardGoodsList, goodsId));
        request.getSession().setAttribute("cardGoodsList", cardGoodsList);

    }

    @SuppressWarnings("unchecked")
    private void showGoods(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        logger.info("Method showGoods is started");
        cardGoodsList = (List<Goods>) request.getSession().getAttribute("cardGoodsList");
        if(cardGoodsList != null) {
            sendGoodsList(request, response, cardGoodsList);
        } else {
            logger.debug("Redirect to the user/card_page.jsp, cardGoodsList = null");
            response.sendRedirect("user/card_page.jsp");
        }
    }

    private Goods getGoodsById(List<Goods> goodsList, int id){
        Goods goods1 = new Goods();
        for (Goods goods : goodsList){
            if(goods.getId() == id){
                goods1 = goods;
            }
        }
        return goods1;
    }

    private void sendGoodsList(HttpServletRequest request, HttpServletResponse response, List<Goods> goodsList)
            throws ServletException, IOException {
        logger.info("Method sendGoodsList is started");
        logger.debug("Redirect to the user/card_page.jsp");
        response.sendRedirect("user/card_page.jsp");
    }
}
