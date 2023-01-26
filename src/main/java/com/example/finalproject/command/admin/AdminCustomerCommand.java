package com.example.finalproject.command.admin;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IRoleDAO;
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

public class AdminCustomerCommand implements ICommand {
    DAOFactory daoFactory;
    IUserDAO userDAO;
    IRoleDAO roleDAO;
    int startPage = 1;
    int recordsPerPage = 5;
    String listParam;
    String lastMenu;
    List<User> userList;
    private static final Logger logger = LogManager.getLogger(AdminCustomerCommand.class);

    public AdminCustomerCommand() {
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        userDAO = daoFactory.getUserDAO();
        roleDAO = daoFactory.getRoleDAO();
        listParam = "";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException, NamingException, ClassNotFoundException {
        showList(request, response);
    }

    private void showList(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, NamingException, ClassNotFoundException {
        logger.info("The showList method is started");
        String action = request.getParameter("action");
        try {
            switch (action){
            case "showList":
                showUser(request, response);
            case "block":
                blockUser(request, response);
                showUser(request, response);
                break;
            case "unblock":
                unblockUser(request, response);
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

    private void blockUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException, ClassNotFoundException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.blockUser(userId);
    }

    private void unblockUser(HttpServletRequest request, HttpServletResponse response) throws IOException, SQLException, NamingException, ClassNotFoundException {
        int userId = Integer.parseInt(request.getParameter("userId"));
        userDAO.unblockUser(userId);
    }

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
                //request.setAttribute("noOfPages", pagination(request, response, countOfUsers));
                startPage = Pagination.pagination(request, countOfUsers, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                userList = userDAO.showLimitUsers((startPage-1)*recordsPerPage, recordsPerPage);
                sendUserList(request, response, userList, countOfUsers);
                break;
            case "blockedList":
                changeStartPageIfChangeMenu(listParam);
                countOfUsers = userDAO.showCountOfUsersByBlockedStatus(true);
                startPage = Pagination.pagination(request, countOfUsers, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                //request.setAttribute("noOfPages", pagination(request, response, countOfUsers));
                userList = userDAO.showLimitUsersByBlockedStatus((startPage-1)*recordsPerPage, recordsPerPage, true);
                sendUserList(request, response, userList, countOfUsers);
                break;
            case "unblockedList":
                changeStartPageIfChangeMenu(listParam);
                countOfUsers = userDAO.showCountOfUsersByBlockedStatus(false);
                //request.setAttribute("noOfPages", pagination(request, response, countOfUsers));
                startPage = Pagination.pagination(request, countOfUsers, startPage, recordsPerPage);
                request.setAttribute("noOfPages", startPage);
                userList = userDAO.showLimitUsersByBlockedStatus((startPage-1)*recordsPerPage, recordsPerPage, false);
                sendUserList(request, response, userList,  countOfUsers);
                break;
            default:
                logger.debug("Case default, action {}, forward to tho admin_customer_list.jsp", listParam);
                RequestDispatcher dispatcher = request.getRequestDispatcher("admin/admin_customer_list.jsp");
                dispatcher.forward(request, response);
                break;
        }
    }

    private void sendUserList(HttpServletRequest request, HttpServletResponse response, List<User> userList, int countOfUsers)
            throws ServletException, IOException {
        logger.info("The sendUserList method is started");
        request.setAttribute("userList", userList);
        request.setAttribute("currentPage", startPage);
        logger.debug("Forward to tho admin_customer_list.jsp");
        RequestDispatcher requestDispatcher = request.getRequestDispatcher("admin/admin_customer_list.jsp");
        requestDispatcher.forward(request, response);
    }

    private void changeStartPageIfChangeMenu(String currentMenu){
        if(!currentMenu.equals(lastMenu)) {
            startPage = 1;
            lastMenu = currentMenu;
        }
    }
}
