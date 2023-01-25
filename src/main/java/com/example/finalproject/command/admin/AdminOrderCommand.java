package com.example.finalproject.command.admin;

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

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class AdminOrderCommand implements ICommand {
    DAOFactory daoFactory;
    IUserDAO userDAO;
    IRoleDAO roleDAO;
    IOrderDAO orderDAO;
    IOrderStatusDAO orderStatusDAO;
    int startPage = 1;
    int recordsPerPage = 5;
    String listParam;
    String lastMenu;
    List<Order> orderList;
    private static final Logger logger = LogManager.getLogger(AdminOrderCommand.class);

    public AdminOrderCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        roleDAO = daoFactory.getRoleDAO();
        orderDAO = daoFactory.getOrderDAO();
        orderStatusDAO = daoFactory.getOrderStatusDAO();
        userDAO = daoFactory.getUserDAO();
        listParam = "";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        showList(request, response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("The showList addProduct is started");
        String action = request.getParameter("action");
        try {
            switch (action){
            case "showList":
                showOrder(request, response);
                break;
            case "paid":
                changeOrderStatus(request, response);
                showOrder(request, response);
                break;
            case "canceled":
                changeOrderStatus(request, response);
                showOrder(request, response);
                break;
            default:
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_order_list.jsp");
                dispatcher.forward(request, response);
                break;
            }
        } catch (SQLException | NamingException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void changeOrderStatus(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException, ClassNotFoundException {
        logger.info("The showList changeOrderStatus is started");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int ordersStatus = orderDAO.getOrderById(orderId).get(0).getOrderStatusId();
        String orderName = orderStatusDAO.getOrderStatusById(ordersStatus).get(0).getName();
        String updatedStatus = request.getParameter("action");
        if(orderName.equals("registered")){
            orderStatusDAO.changeOrderStatus(orderId, orderDAO.selectOrderIdByName(updatedStatus));
        } else {
            request.setAttribute("NOTIFICATION", "You cannot change status for paid or canceled order");
        }
    }

    private void showOrder(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        logger.info("The showList showOrder is started");
        int countOfOrders;
        if(request.getParameter("list")!=null){
            listParam = request.getParameter("list");
        }
        switch (listParam){
            case "registeredList":
                changeStartPageIfChangeMenu(listParam);
                countOfOrders = orderDAO.showCountOfOrders("registered");
                startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                orderList = orderDAO.showLimitOrders((startPage-1)*recordsPerPage, recordsPerPage, "registered");
                sendOrderList(request, response, orderList);
                //formList(request, response, listParam, "registered");
                break;
            case "paidList":
                changeStartPageIfChangeMenu(listParam);
                countOfOrders = orderDAO.showCountOfOrders("paid");
                startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                orderList = orderDAO.showLimitOrders((startPage-1)*recordsPerPage, recordsPerPage, "paid");
                sendOrderList(request, response, orderList);
                //formList(request, response, listParam, "paid");
                break;
            case "canceledList":
                changeStartPageIfChangeMenu(listParam);
                countOfOrders = orderDAO.showCountOfOrders("canceled");
                startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                orderList = orderDAO.showLimitOrders((startPage-1)*recordsPerPage, recordsPerPage, "canceled");
                sendOrderList(request, response, orderList);
                //formList(request, response, listParam, "canceled");
                break;
            default:
                logger.debug("Case default, listParam is {}, Forward to tho admin_order_list.jsp", listParam);
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_order_list.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

//    private void formList(HttpServletRequest request, HttpServletResponse response, String listParam, String orderStatus) throws ServletException, IOException {
//        changeStartPageIfChangeMenu(listParam);
//        int countOfOrders = orderDAO.showCountOfOrders("registered");
//        startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
//        request.setAttribute("noOfPages", startPage);
//        orderList = orderDAO.showLimitOrders((startPage-1)*recordsPerPage, recordsPerPage, "registered");
//        sendOrderList(request, response, orderList);
//    }

    private void sendOrderList(HttpServletRequest request, HttpServletResponse response, List<Order> ordersList)
            throws ServletException, IOException {
        logger.info("The showList sendOrderList is started");
        request.setAttribute("orderList", ordersList);
        request.setAttribute("currentPage", startPage);
        logger.debug("Forward to tho admin_order_list.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("admin/admin_order_list.jsp");
        requestDispatcher.forward(request, response);
    }

    private void changeStartPageIfChangeMenu(String currentMenu){
        if(!currentMenu.equals(lastMenu)) {
            startPage = 1;
            lastMenu = currentMenu;
        }
    }
}
