package com.example.finalproject.dao.mysql;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.GenericDAO;
import com.example.finalproject.dao.IRoleDAO;
import com.example.finalproject.dao.IUserDAO;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.Login;
import com.example.finalproject.models.Order;
import com.example.finalproject.models.User;
import com.example.finalproject.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDAO extends GenericDAO<User> implements IUserDAO {
    private static final Logger logger = LogManager.getLogger(UserDAO.class);
    private UserDAO(){
    }
    DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
    IRoleDAO roleDAO = daoFactory.getRoleDAO();

    private static UserDAO instance;
    public static IUserDAO getInstance() {
        if (instance == null)
            instance = new UserDAO();
        return instance;
    }

    @Override
    public boolean validate(Login login) throws ClassNotFoundException {
        boolean status = false;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(DBQuery.SELECT_USER_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, login.getUsername());
            preparedStatement.setString(2, login.getPassword());
            ResultSet rs = preparedStatement.executeQuery();
            status = rs.next();
        } catch (SQLException e) {
            logger.error(e);
            JDBCUtils.printSQLException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return status;
    }

    @Override
    public int getUserId(Login loginBean){
        int userId = 0;
        try (Connection connection = JDBCUtils.getConnection();
             PreparedStatement preparedStatement = connection
                     .prepareStatement(DBQuery.SELECT_USER_ID_BY_LOGIN_AND_PASSWORD)) {
            preparedStatement.setString(1, loginBean.getUsername());
            preparedStatement.setString(2, loginBean.getPassword());

            ResultSet rs = preparedStatement.executeQuery();
            if(rs.next()) {
                userId = rs.getInt("id");
            }
        } catch (SQLException e) {
            logger.error(e);
            JDBCUtils.printSQLException(e);
        } catch (ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            logger.error(e);
            throw new RuntimeException(e);
        }
        return userId;
    }

    @Override
    public boolean createUser(User user) {
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBQuery.INSERT_USER)) {
            mapFromEntity(statement, user);
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean createOrder(int userId, int goodsId){
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBQuery.INSERT_ORDER)) {
            statement.setInt(1, goodsId);
            statement.setInt(2, userId);
            statement.setInt(3, getOrderStatusIdRegistered());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public List<Order> showOrdersByUser(int id){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_BY_USER)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(new Order(rs.getInt("id"), rs.getInt("goods_id"),
                        rs.getInt("users_id"), rs.getInt("orders_status_id")));
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return ordersList;
    }

    @Override
    public boolean updateName(int id, String name){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_NAME)) {
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean updateSurname(int id, String surname){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_SURNAME)) {
            preparedStatement.setString(1, surname);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean updateLogin(int id, String login){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_LOGIN)) {
            preparedStatement.setString(1, login);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean updatePassword(int id, String password){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_PASSWORD)) {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean updateEmail(int id, String email){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_EMAIL)) {
            preparedStatement.setString(1, email);
            preparedStatement.setInt(2, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public int getRoleIdUser() {
        int id = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ROLE_ID_BY_NAME)) {
            preparedStatement.setString(1, "user");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return id;
    }

    @Override
    public int getOrderStatusIdRegistered(){
        int id = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_STATUS_ID_BY_NAME)) {
            preparedStatement.setString(1, "registered");
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException | ClassNotFoundException | NamingException e){
            logger.error(e);
            e.printStackTrace();
        }
        return id;
    }

    //TODO
    @Override
    public List<User> getUserById(Integer id) {
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_USER_BY_ID);
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public boolean updateUser(User user) {
        boolean rowUpdated = false;
        try (Connection connection = JDBCUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBQuery.UPDATE_USER)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getSurname());
            statement.setString(3, user.getLogin());
            statement.setString(4, user.getPassword());
            statement.setString(5, user.getEmail());
            statement.setInt(6, user.getId());
            rowUpdated = statement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException | NamingException e) {
            logger.error(e);
            e.printStackTrace();
        }
        return rowUpdated;
    }

    @Override
    public boolean deleteUser(User user) {
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBQuery.DELETE_USER_BY_LOGIN)) {
            statement.setString(1, user.getLogin());
            statement.executeUpdate();
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
            return false;
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    @Override
    public boolean deleteUserById(int id) {
        boolean rowDeleted = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement statement = connection.prepareStatement(DBQuery.DELETE_USER_BY_ID)) {
            statement.setInt(1, id);
            rowDeleted = statement.executeUpdate() > 0;
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return rowDeleted;
    }

    @Override
    public List<User> readUserByLogin(String login) {
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_USER_BY_LOGIN);
            preparedStatement.setString(1, login);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()) {
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public List<User> selectAllUsers() {
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(DBQuery.SELECT_ALL_FROM_USERS)) {
            while (rs.next()){
                userList.add(mapToEntity(rs));
            }

        } catch (SQLException | ClassNotFoundException e) {
            logger.error(e);
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    //TODO refactoring

    @Override
    public List<User> showAllUsers(){
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ALL_FROM_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public List<User> showLimitUsers(int from, int numberOfRecords){
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_LIMIT_USERS)) {
            preparedStatement.setInt(1, roleDAO.getUserId());
            preparedStatement.setInt(2, from);
            preparedStatement.setInt(3, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public List<User> showLimitUsersByBlockedStatus(int from, int numberOfRecords, boolean isBlocked){
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_LIMIT_USERS_BY_BLOCKED_STATUS)) {
            preparedStatement.setInt(1, roleDAO.getUserId());
            preparedStatement.setBoolean(2, isBlocked);
            preparedStatement.setInt(3, from);
            preparedStatement.setInt(4, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public List<User> showBlockedUsers(){
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_BLOCKED_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public List<User> showUnblockedUsers(){
        List<User> userList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_UNBLOCKED_USERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                userList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return userList;
    }

    @Override
    public int showCountOfUsers(){
        int count = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_NUMBER_OF_USERS)) {
            preparedStatement.setInt(1, roleDAO.getUserId());
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public int showCountOfUsersByBlockedStatus(boolean statusBlocked){
        int count = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_NUMBER_OF_USERS_BY_BLOCKED_STATUS)) {
            preparedStatement.setInt(1, roleDAO.getUserId());
            preparedStatement.setBoolean(2, statusBlocked);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return count;
    }

    @Override
    public boolean blockUser(int id){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_STATUS_BLOCKED_TRUE)) {
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    public boolean unblockUser(int id){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_USER_STATUS_BLOCKED_FALSE)) {
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected User mapToEntity(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"), rs.getString("name"), rs.getString("surname"),
                rs.getString("login"), rs.getString("password"), rs.getString("email"),
                rs.getInt("roles_id"), rs.getBoolean("status_blocked"));
    }

    @Override
    protected PreparedStatement mapFromEntity(PreparedStatement preparedStatement, User user) throws SQLException {
        preparedStatement.setString(1, user.getName());
        preparedStatement.setString(2, user.getSurname());
        preparedStatement.setString(3, user.getLogin());
        preparedStatement.setString(4, user.getPassword());
        preparedStatement.setString(5, user.getEmail());
        preparedStatement.setInt(6, getRoleIdUser());
        return preparedStatement;
    }
}
