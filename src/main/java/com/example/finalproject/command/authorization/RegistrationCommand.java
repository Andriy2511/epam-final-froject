package com.example.finalproject.command.authorization;

import com.example.finalproject.command.ICommand;
import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.models.User;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.sql.SQLException;

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

    private void register(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
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
                request.setAttribute("NOTIFICATION", "User Registered Successfully!");
                logger.debug("Forward to tho login/login.jsp, notification User Registered Successfully!");
                response.sendRedirect("login/login.jsp?NOTIFICATION=User Registered Successfully!");
            } else {
                request.setAttribute("NOTIFICATION", "User must contain unique login and e-mail address!");
                logger.debug("Forward to tho register/register.jsp, notification User must contain unique login and e-mail address!");
                response.sendRedirect("register/register.jsp?NOTIFICATION=User must contain unique login and e-mail address!");
            }
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        }
    }
}
