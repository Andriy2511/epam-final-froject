package com.example.finalproject.command.authorization;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IRoleDAO;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.Login;
import com.example.finalproject.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

public class LoginCommand implements ICommand {

	DAOFactory daoFactory;
	IUserDAO userDao;
	IRoleDAO roleDAO;
	private static final Logger logger = LogManager.getLogger(LoginCommand.class);

	public LoginCommand(){
		daoFactory = DAOFactory.getDaoFactory("MYSQL");
		userDao = daoFactory.getUserDAO();
		roleDAO = daoFactory.getRoleDAO();
	}

	@Override
	public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
		authenticate(request, response);
	}

	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		logger.info("Method authorization is started");
		String username = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession httpSession = request.getSession();
		Login login = new Login();
		login.setUsername(username);
		login.setPassword(password);
		try {
			boolean loginSuccessful = userDao.validate(login);
			if (loginSuccessful) {
				if(!isUserBlocked(username)) {
					User user = userDao.readUserByLogin(username).get(0);
					if (roleDAO.getAdminId() == getUserRole(username)) {
						httpSession.setAttribute("id", userDao.getUserId(login));
						httpSession.setAttribute("roleId", user.getRoleId());
						httpSession.setAttribute("user", user);
						httpSession.setAttribute("userRole", "admin");
						logger.debug("Redirection to the {}", request.getContextPath() + "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList, role admin");
						response.sendRedirect(request.getContextPath()+"/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
					} else if (roleDAO.getUserId() == getUserRole(username)) {
						httpSession.setAttribute("id", userDao.getUserId(login));
						httpSession.setAttribute("roleId", user.getRoleId());
						httpSession.setAttribute("user", user);
						httpSession.setAttribute("userRole", "user");
						logger.debug("Redirection to the {}", request.getContextPath() + "/FrontController?command=CATALOG_COMMAND&action=showGoodsList, role user");
						response.sendRedirect(request.getContextPath() + "/FrontController?command=CATALOG_COMMAND&action=showGoodsList");
					} else {
						logger.debug("Redirection to login/login.jsp");
						response.sendRedirect("login/login.jsp");
					}
				} else {
					writeNotification(request, response, "Account were blocked by admin");
				}
			} else {
				writeNotification(request, response, "Invalid login or password!");
			}
		} catch (ClassNotFoundException | NamingException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	private void writeNotification(HttpServletRequest request, HttpServletResponse response, String notification) throws ServletException, IOException {
		request.setAttribute("NOTIFICATION", notification);
		logger.debug("Forward to the login/login.jsp, notification {}", notification);
		RequestDispatcher dispatcher = request.getRequestDispatcher("login/login.jsp");
		dispatcher.forward(request, response);
	}

	private boolean isUserBlocked(String username){
		User user = userDao.readUserByLogin(username).get(0);
		return user.getStatusBlocked();
	}
	private int getUserRole(String login){
		User user = userDao.readUserByLogin(login).get(0);
		return user.getRoleId();
	}
}
