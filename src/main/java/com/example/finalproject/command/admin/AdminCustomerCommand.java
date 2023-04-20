package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.User;
import com.example.finalproject.pagination.Pagination;
import jakarta.servlet.*;
import jakarta.servlet.http.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

/**
 * AdminCustomerCommand class is designed to manage users. This class is responsible for blocking/unblocking user and showing the list of users.
 */
public class AdminCustomerCommand implements ICommand {
    private IUserDAO userDAO;
    private int startPage = 1;
    private int recordsPerPage = 5;
    private String listParam;
    private String lastMenu;
    private List<User> userList;
    private String action;
    private static final Logger logger = LogManager.getLogger(AdminCustomerCommand.class);

    public AdminCustomerCommand() {
        DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
        userDAO = daoFactory.getUserDAO();
        listParam = "";
        action = "";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NamingException, ClassNotFoundException {
        showList(request, response);
    }

    /**
     * This method determines what action the user performed and show appropriate list.
     * The method receives the "action" parameter from the request and passes control to appropriate methods.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logger.info("The showList method is started");
        action = request.getParameter("action");
        try {
            switch (action){
            case "showList":
                showUser(request, response);
                break;
            case "block":
                blockUser(request);
                showUser(request, response);
                break;
            case "unblock":
                unblockUser(request);
                showUser(request, response);
                break;
            default:
                logger.debug("Case default, action {}, forward to tho admin_customer_list.jsp", action);
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_customer_list.jsp");
                dispatcher.forward(request, response);
                break;
            }
        } catch (SQLException e) {
            logger.error(e);
            e.printStackTrace();
        }
    }

    /**
     * The method for blocking users
     * @param request - HttpServletRequest
     */
    private void blockUser(HttpServletRequest request) throws SQLException {
        logger.info("The blockUser method is started");
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.blockUser(userId);
    }

    /**
     * The method for unblocking users
     * @param request - HttpServletRequest
     */
    private void unblockUser(HttpServletRequest request) throws SQLException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.unblockUser(userId);
    }

    /**
     * This method determines which list should be shown. The parameter of the desired list is obtained from the request list.
     * List can contain full, block or unblock users.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void showUser(HttpServletRequest request, HttpServletResponse response) throws ServletException, SQLException, IOException {
        logger.info("The showList method is started");
        int countOfUsers;
        if(request.getParameter("list")!=null){
            listParam = request.getParameter("list");
        }
        switch (listParam){
            case "fullList":
                changeStartPageIfChangeMenu(listParam);
                countOfUsers = userDAO.showCountOfUsers();
                startPage = Pagination.pagination(request, countOfUsers, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                userList = userDAO.showLimitUsers((startPage-1)*recordsPerPage, recordsPerPage);
                sendUserList(request, response, userList);
                break;
            case "blockedList":
                changeStartPageIfChangeMenu(listParam);
                countOfUsers = userDAO.showCountOfUsersByBlockedStatus(true);
                formListOfUserByStatus(request, response, countOfUsers, true);
                break;
            case "unblockedList":
                changeStartPageIfChangeMenu(listParam);
                countOfUsers = userDAO.showCountOfUsersByBlockedStatus(false);
                formListOfUserByStatus(request, response, countOfUsers, false);
                break;
            default:
                logger.debug("Case default, action {}, forward to tho admin_customer_list.jsp", listParam);
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_customer_list.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    /**
     * Form a user list by blocked status.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param countOfUsers - count of users
     * @param statusBlocked - user block status
     */
    private void formListOfUserByStatus(HttpServletRequest request, HttpServletResponse response, int countOfUsers, boolean statusBlocked) throws IOException {
        startPage = Pagination.pagination(request, countOfUsers, startPage, recordsPerPage);
        request.setAttribute("noOfPages", startPage);
        userList = userDAO.showLimitUsersByBlockedStatus((startPage-1)*recordsPerPage, recordsPerPage, statusBlocked);
        sendUserList(request, response, userList);
    }

    /**
     * This method send action, page of list and notification to the admin_order_list.jsp page.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     * @param userList - list of users
     */
    private void sendUserList(HttpServletRequest request, HttpServletResponse response, List<User> userList)
            throws IOException {
        logger.info("The sendUserList method is started");
        request.getSession().setAttribute("userList", userList);
        logger.debug("Forward to tho admin_customer_list.jsp");
        response.sendRedirect(request.getContextPath() +
                "/admin/admin_customer_list.jsp?action=" + action + "&list=" + listParam +
                "&" + "noOfPages=" + startPage);
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
