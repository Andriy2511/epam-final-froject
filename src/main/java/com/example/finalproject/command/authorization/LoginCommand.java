package com.example.finalproject.command.authorization;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IRoleDAO;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.io.IOException;
import java.sql.SQLException;

/**
 * The LoginCommand class is responsible for authentication and authorization.
 */
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

	/**
	 * This method gets the username and password parameters from the request.The method then checks that the data is correct.
	 * If the data is incorrect or the user is locked out by an administrator, the method transfer control to the writeNotification method,
	 * which sends a redirect to the login.jsp page with the appropriate notification. If the data is correct and the user is unlocked,
	 * the method determines the user's roles (admin or user) and redirects to the admin or user page.
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 */
	private void authenticate(HttpServletRequest request, HttpServletResponse response) throws IOException {
		logger.info("Method authorization is started");
		String username = request.getParameter("login");
		String password = request.getParameter("password");
		HttpSession httpSession = request.getSession();
		User login = new User();
		login.setLogin(username);
		login.setPassword(password);
		try {
			boolean loginSuccessful = userDao.validate(login);
			if (loginSuccessful) {
				if(!isUserBlocked(username)) {
					User user = userDao.readUserByLogin(username).get(0);
					if (roleDAO.getAdminId() == getUserRole(username)) {
						httpSession.setAttribute("id", userDao.getUserId(user));
						httpSession.setAttribute("roleId", user.getRoleId());
						httpSession.setAttribute("user", user);
						httpSession.setAttribute("userRole", "admin");
						logger.debug("Redirection to the {}", request.getContextPath() + "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList, role admin");
						response.sendRedirect(request.getContextPath()+"/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
					} else if (roleDAO.getUserId() == getUserRole(username)) {
						httpSession.setAttribute("id", userDao.getUserId(user));
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
		} catch (ClassNotFoundException |NamingException  e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * This method sends redirect to the login.jsp page and records the notification as a parameter
	 * @param request - HttpServletRequest
	 * @param response - HttpServletResponse
	 * @param notification - the notification that will be sent to the user
	 */
	private void writeNotification(HttpServletRequest request, HttpServletResponse response, String notification) throws IOException {
		logger.debug("Forward to the login/login.jsp, notification {}", notification);
		response.sendRedirect("login/login.jsp" + "?NOTIFICATION=" + notification);
	}

	/**
	 * This method checks if the user is blocked by administrator
	 * @param username - user login
	 * @return - boolean result is user blocked or not
	 */
	private boolean isUserBlocked(String username){
		User user = userDao.readUserByLogin(username).get(0);
		return user.getStatusBlocked();
	}

	/**
	 * This method gets user id by login
	 * @param login - user login
	 * @return - an integer value that indicates the role of the user
	 */
	private int getUserRole(String login){
		User user = userDao.readUserByLogin(login).get(0);
		return user.getRoleId();
	}
}
