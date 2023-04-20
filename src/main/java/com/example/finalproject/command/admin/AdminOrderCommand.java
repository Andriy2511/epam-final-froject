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

/**
 * The AdminOrderCommand class is responsible for managing orders
 */
public class AdminOrderCommand implements ICommand {
    private IOrderDAO orderDAO;
    private IOrderStatusDAO orderStatusDAO;
    private int startPage = 1;
    private String listParam;
    private String lastMenu;
    private String notification;
    private String action;
    private static final Logger logger = LogManager.getLogger(AdminOrderCommand.class);

    public AdminOrderCommand(){
        DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
        orderDAO = daoFactory.getOrderDAO();
        orderStatusDAO = daoFactory.getOrderStatusDAO();
        listParam = "";
        notification = "";
        action = "";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        showList(request, response);
    }

    /**
     * This method determines what action the user performed and displays the corresponding list of orders.
     * The method receives the "action" parameter from the request and passes control to appropriate methods.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("The showList addProduct is started");
        notification = "";
        action = request.getParameter("action");
        try {
            switch (action){
            case "showList":
                showOrder(request, response);
                break;
            case "paid":
                case "canceled":
                    changeOrderStatus(request);
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

    /**
     * The method changes the status of the order from registered to paid or canceled. The status of a paid or canceled order cannot be changed.
     * @param request - HttpServletRequest
     */
    private void changeOrderStatus(HttpServletRequest request) throws SQLException, NamingException, ClassNotFoundException {
        logger.info("The showList changeOrderStatus is started");
        int orderId = Integer.parseInt(request.getParameter("orderId"));
        int ordersStatus = orderDAO.getOrderById(orderId).get(0).getOrderStatusId();
        String orderName = orderStatusDAO.getOrderStatusById(ordersStatus).get(0).getName();
        String updatedStatus = request.getParameter("action");
        if(orderName.equals("registered")){
            orderStatusDAO.changeOrderStatus(orderId, orderDAO.selectOrderIdByName(updatedStatus));
        } else {
            notification = "locale.MessageCannotChangeOrderStatus";
        }
    }

    /**
     * The method show appropriate list of orders by parameter "list". If parameter "list" is null, forward to the admin_order_list.jsp.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showOrder(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        logger.info("The showList showOrder is started");
        if(request.getParameter("list")!=null){
            listParam = request.getParameter("list");
        }
        switch (listParam){
            case "registeredList":
                formList(request, response, listParam, "registered");
                break;
            case "paidList":
                formList(request, response, listParam, "paid");
                break;
            case "canceledList":
                formList(request, response, listParam, "canceled");
                break;
            default:
                logger.debug("Case default, listParam is {}, Forward to tho admin_order_list.jsp", listParam);
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_order_list.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    /**
     * Forms a list that will be shown to the administrator.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param listParam - value of list parameters. Value may be registeredList, paidList, canceledList.
     * @param orderStatus - value of order status. Value may be registered, paid, canceled.
     * @see #showOrder(HttpServletRequest, HttpServletResponse)
     */
    private void formList(HttpServletRequest request, HttpServletResponse response, String listParam, String orderStatus) throws IOException {
        changeStartPageIfChangeMenu(listParam);
        int countOfOrders = orderDAO.showCountOfOrders(orderStatus);
        int recordsPerPage = 5;
        startPage = Pagination.pagination(request, countOfOrders, startPage, recordsPerPage);
        request.setAttribute("noOfPages", startPage);
        List<Order> orderList = orderDAO.showLimitOrders((startPage - 1) * recordsPerPage, recordsPerPage, orderStatus);
        sendOrderList(request, response, orderList);
    }
    /**
     * This method send action, page of list and notification to the admin_order_list.jsp page.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param ordersList - list of orders
     */
    private void sendOrderList(HttpServletRequest request, HttpServletResponse response, List<Order> ordersList)
            throws IOException {
        logger.info("The showList sendOrderList is started");
        request.getSession().setAttribute("orderList", ordersList);
        logger.debug("Forward to tho admin_order_list.jsp");
        response.sendRedirect(request.getContextPath() +
                "/admin/admin_order_list.jsp?action=" + action +
                "&" + "NOTIFICATION=" + notification + "&" + "noOfPages="
                + startPage);
    }

    /**
     * This method sets the first page if the admin change menu.
     * @param currentMenu - current displayed menu
     */
    private void changeStartPageIfChangeMenu(String currentMenu){
        if(!currentMenu.equals(lastMenu)) {
            startPage = 1;
            lastMenu = currentMenu;
        }
    }
}
