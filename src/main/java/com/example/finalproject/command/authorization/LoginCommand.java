package com.example.finalproject.command.authorization;

import com.example.finalproject.captcha.Captcha;
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
	private IUserDAO userDao;
	private IRoleDAO roleDAO;
	private static final Logger logger = LogManager.getLogger(LoginCommand.class);

	public LoginCommand(){
		DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
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
		String username = request.getParameter("login");
		String password = request.getParameter("password");
		try {
			User user = buildUser(username, password);
			if(Captcha.isCaptchaPassed(request)) {
				if (userDao.validate(user)) {
					if (!isUserBlocked(username)) {
						redirectUser(request, response, user);
					} else {
						writeNotification(response, "locale.MessageAccountBlocked");
					}
				} else {
					writeNotification(response, "locale.MessageInvalidLogin");
				}
			} else {
				writeNotification(response, "locale.MessageCaptchaNotPassed");
			}
		} catch (ClassNotFoundException | NamingException | NullPointerException e) {
			logger.error(e);
			e.printStackTrace();
		}
	}

	/**
	 * This method sends redirect to the login.jsp page and records the notification as a parameter
	 * @param response - HttpServletResponse
	 * @param notification - the notification that will be sent to the user
	 */
	private void writeNotification(HttpServletResponse response, String notification) throws IOException {
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
	 * @param login user login
	 * @return an integer value that indicates the role of the user
	 */
	private int getUserRole(String login){
		User user = userDao.readUserByLogin(login).get(0);
		return user.getRoleId();
	}

	/**
	 * Builds a User object from the given HttpServletRequest
	 *
	 * @param username Users name
	 * @param password Users password
	 * @return a new User object containing the login and password
	 */
	private User buildUser(String username, String password) {
		User user = new User();
		user.setLogin(username);
		user.setPassword(password);
		logger.info("User is {}", user);
		return user;
	}

	/**
	 * Redirects the user to the appropriate page based on their role.
	 *
	 * @param request HttpServletRequest
	 * @param response HttpServletRequest
	 * @param user the role of the user, either "admin" or "user"
	 */
	private void redirectUser(HttpServletRequest request, HttpServletResponse response, User user) throws IOException, NamingException, ClassNotFoundException {
		HttpSession session = request.getSession();
		int roleId = getUserRole(user.getLogin());
		session.setAttribute("id", userDao.getUserId(user));
		session.setAttribute("roleId", roleId);
		session.setAttribute("user", user);
		if (roleDAO.getAdminId() == roleId) {
			session.setAttribute("userRole", "admin");
			logger.debug("Redirection to the {}", request.getContextPath() + "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList, role admin");
			response.sendRedirect(request.getContextPath() + "/FrontController?command=ADMIN_PRODUCT_CONTROLLER&action=showGoodsList");
		} else if (roleDAO.getUserId() == roleId) {
			session.setAttribute("userRole", "user");
			logger.debug("Redirection to the {}", request.getContextPath() + "/FrontController?command=CATALOG_COMMAND&action=showGoodsList, role user");
			response.sendRedirect(request.getContextPath() + "/FrontController?command=CATALOG_COMMAND&action=showGoodsList");
		} else {
			logger.debug("Redirection to login/login.jsp");
			response.sendRedirect("login/login.jsp");
		}
	}
}
