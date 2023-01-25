package com.example.finalproject.command.user;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.admin.AddProductCommand;
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

public class UserOrderCommand implements ICommand {
    DAOFactory daoFactory;
    IUserDAO userDAO;
    IRoleDAO roleDAO;
    IOrderDAO orderDAO;
    IOrderStatusDAO orderStatusDAO;
    int startPage = 1;
    int recordsPerPage = 5;
    private static final Logger logger = LogManager.getLogger(UserOrderCommand.class);
    List<Order> orderList;

    public UserOrderCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        roleDAO = daoFactory.getRoleDAO();
        orderDAO = daoFactory.getOrderDAO();
        orderStatusDAO = daoFactory.getOrderStatusDAO();
        userDAO = daoFactory.getUserDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        showList(request, response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("Method showList is started");
        int countOfOrders;
        countOfOrders = orderDAO.showCountOfOrdersByUserId((Integer) request.getSession().getAttribute("id"));
        startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
        request.setAttribute("noOfPages", startPage);
        orderList = orderDAO.showLimitOrdersByUser((startPage-1)*recordsPerPage, recordsPerPage, (Integer) request.getSession().getAttribute("id"));
        sendOrderList(request, response, orderList);
    }

    private void sendOrderList(HttpServletRequest request, HttpServletResponse response, List<Order> ordersList)
            throws ServletException, IOException {
        logger.info("Method sendOrderList is started");
        request.setAttribute("orderList", ordersList);
        request.setAttribute("currentPage", startPage);
        logger.debug("Forward to the user/user_order_list.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("user/user_order_list.jsp");
        requestDispatcher.forward(request, response);
    }
}
