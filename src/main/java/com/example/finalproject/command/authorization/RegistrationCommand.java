package com.example.finalproject.command.authorization;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.User;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

/**
 * The RegistrationCommand class implements the ICommand interface and is responsible for registration.
 */
public class RegistrationCommand implements ICommand {

    DAOFactory daoFactory;
    IUserDAO userDao;
    private static final Logger logger = LogManager.getLogger(RegistrationCommand.class);

    public RegistrationCommand(){
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        userDao = daoFactory.getUserDAO();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
        register(request, response);
    }

    /**
     * The method receives the parameters from the request and attempts to register the user.
     * After registration, the method sends a redirect to the login.jsp page if the user has successfully registered,
     * and to the register.jsp page with a corresponding message if the registration has failed.
     * @param request - HttpServletRequest
     * @param response - HttpServletResponse
     */
    private void register(HttpServletRequest request, HttpServletResponse response) {
        logger.info("Method register is started");
        String name = request.getParameter("name");
        String surname = request.getParameter("surname");
        String login = request.getParameter("login");
        String password = request.getParameter("password");
        String email = request.getParameter("email");

        User user = new User();
        user.setName(name);
        user.setSurname(surname);
        user.setLogin(login);
        user.setPassword(password);
        user.setEmail(email);

        try {
            boolean successfulRegistration = userDao.createUser(user);
            if(successfulRegistration) {
                logger.debug("Forward to tho login/login.jsp, notification User Registered Successfully!");
                response.sendRedirect("login/login.jsp?NOTIFICATION=locale.RegistrationSuccessful");
            } else {
                logger.debug("Forward to tho register/register.jsp, notification User must contain unique login and e-mail address!");
                response.sendRedirect("register/register.jsp?NOTIFICATION=locale.RegistrationUnsuccessful");
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
