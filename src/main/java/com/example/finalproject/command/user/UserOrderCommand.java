package com.example.finalproject.command.user;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.*;
import com.example.finalproject.models.Order;
import com.example.finalproject.pagination.Pagination;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * The UserOrderCommand class implements the ICommand interface and is responsible for viewing orders by users. The user can view his orders and their status.
 */
public class UserOrderCommand implements ICommand {
    private IOrderDAO orderDAO;
    private int startPage = 1;
    private static final Logger logger = LogManager.getLogger(UserOrderCommand.class);
    List<Order> orderList;

    public UserOrderCommand(){
        DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
        orderDAO = daoFactory.getOrderDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        showList(request, response);
    }

    /**
     * This method displays the user's orders
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Method showList is started");
        int countOfOrders;
        countOfOrders = orderDAO.showCountOfOrdersByUserId((Integer) request.getSession().getAttribute("id"));
        int recordsPerPage = 5;
        startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
        request.setAttribute("noOfPages", startPage);
        orderList = orderDAO.showLimitOrdersByUser((startPage-1)* recordsPerPage, recordsPerPage, (Integer) request.getSession().getAttribute("id"));
        sendOrderList(request, response, orderList);
    }

    /**
     * This method sets the order list to the session and redirects to user_order_list.jsp
     * @param request HttpServletRequest
     * @param response HttpServletResponse
     * @param ordersList list of orders from database
     */
    private void sendOrderList(HttpServletRequest request, HttpServletResponse response, List<Order> ordersList)
            throws ServletException, IOException {
        logger.info("Method sendOrderList is started");
        request.getSession().setAttribute("orderList", ordersList);
        request.setAttribute("currentPage", startPage);
        logger.debug("Forward to the user/user_order_list.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/user_order_list.jsp");
        requestDispatcher.forward(request, response);
    }
}
