package com.example.finalproject;

import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.utils.JDBCUtils;

import javax.naming.NamingException;
import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
//import java.util.logging.Logger;
//import org.apache.logging.log4j.Logger;
//import org.apache.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {
    private static DAOFactory daoFactory;
    private static IUserDAO iUserDAO;
    //private static final Logger log = Logger.getLogger(String.valueOf(Main.class));
    //private static final Logger log = Logger.getLogger(String.valueOf(Main.class));
    private static final Logger logger = LogManager.getLogger(Main.class);
    static {
        daoFactory = DAOFactory.getDaoFactory("MYSQL");
        iUserDAO = daoFactory.getUserDAO();
    }
    public static void main(String[] args) throws SQLException, NamingException, ClassNotFoundException {
//        log.info("logger is working... {}", LocalDateTime.now());
//        System.out.println(new AdminDAO().showCategoryById(2).get(0).getName());
//        JDBCUtils jdbcUtils = new JDBCUtils();
//        Connection connection = (Connection) jdbcUtils.getConnection();
        logger.debug("Debug Message Logged !!!");
        logger.info("Info Message Logged !!!");
        logger.error("Error Message Logged !!!", new NullPointerException("NullError"));


    }


    public static boolean testConnection(){
        JDBCUtils jdbcUtils = new JDBCUtils();
        try(Connection connection = jdbcUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement("select * from users")) {
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()){
                System.out.println(resultSet.getString("name"));
            }
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static String getCurrentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }
}
