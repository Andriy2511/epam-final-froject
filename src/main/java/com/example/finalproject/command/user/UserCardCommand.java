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

/**
 * UserCardCommand class implements ICommand interface and is responsible for adding orders to the card.
 */
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

    /**
     * This method receives an "action" parameter from the request that specifies what user wants to do. The user can confirm or delete his order.
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Method showList is started");
        String action = request.getParameter("action");
        try {
            switch (action){
                case "showGoodsList":
                    showGoods(request, response);
                    break;
                case "confirm":
                    confirmGoods(request);
                    deleteGoods(request);
                    showGoods(request, response);
                    break;
                case "delete":
                    deleteGoods(request);
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

    /**
     * This method calls to confirm the user's order
     * @param request HttpServletRequest
     */
    private void confirmGoods(HttpServletRequest request) throws SQLException, NamingException, ClassNotFoundException {
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        Integer id = (Integer) request.getSession().getAttribute("id");
        orderDAO.addNewOrder(goodsId, id);
    }

    /**
     * Removes the order from the cart
     * @param request HttpServletRequest
     */
    @SuppressWarnings("unchecked")
    private void deleteGoods(HttpServletRequest request) {
        int goodsId = Integer.parseInt(request.getParameter("goodsId"));
        cardGoodsList = (List<Goods>) request.getSession().getAttribute("cardGoodsList");
        cardGoodsList.remove(getGoodsById(cardGoodsList, goodsId));
        request.getSession().setAttribute("cardGoodsList", cardGoodsList);
    }

    /**
     * Show all products that have been added to the card
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    @SuppressWarnings("unchecked")
    private void showGoods(HttpServletRequest request, HttpServletResponse response) throws SQLException, IOException {
        logger.info("Method showGoods is started");
        cardGoodsList = (List<Goods>) request.getSession().getAttribute("cardGoodsList");
        if(cardGoodsList != null) {
            response.sendRedirect("user/card_page.jsp");
        } else {
            logger.debug("Redirect to the user/card_page.jsp, cardGoodsList = null");
            response.sendRedirect("user/card_page.jsp");
        }
    }

    /**
     * Get product from the list of products by id
     * @param goodsList parameterized list with type "Goods"
     * @param id id of the product which must be returned
     * @return object with type "Goods" from the goods list
     */
    private Goods getGoodsById(List<Goods> goodsList, int id){
        Goods product = new Goods();
        for (Goods goods : goodsList){
            if(goods.getId() == id){
                product = goods;
            }
        }
        return product;
    }
}
