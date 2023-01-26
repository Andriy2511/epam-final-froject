package com.example.finalproject.dao;

import com.example.finalproject.models.Login;
import com.example.finalproject.models.Order;
import com.example.finalproject.models.User;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface IUserDAO {
    boolean validate(Login login) throws ClassNotFoundException;

    int getUserId(Login loginBean) throws NamingException, ClassNotFoundException;

    boolean createUser(User user) throws SQLException, NamingException, ClassNotFoundException;

    boolean createOrder(int userId, int goodsId) throws SQLException, NamingException, ClassNotFoundException;

    List<Order> showOrdersByUser(int id);

    boolean updateName(int id, String name) throws SQLException, NamingException, ClassNotFoundException;

    boolean updateSurname(int id, String surname) throws SQLException, NamingException, ClassNotFoundException;

    boolean updateLogin(int id, String login) throws SQLException, NamingException, ClassNotFoundException;

    boolean updatePassword(int id, String password) throws SQLException, NamingException, ClassNotFoundException;

    boolean updateEmail(int id, String email) throws SQLException, NamingException, ClassNotFoundException;

    int getRoleIdUser();

    int getOrderStatusIdRegistered();

    List<User> getUserById(Integer id);

    boolean updateUser(User user) throws SQLException, NamingException, ClassNotFoundException;

    boolean deleteUser(User user) throws SQLException, NamingException, ClassNotFoundException;

    boolean deleteUserById(int id) throws SQLException, NamingException, ClassNotFoundException;

    List<User> readUserByLogin(String login);

    List<User> selectAllUsers();

    List<User> showAllUsers();

    List<User> showLimitUsers(int from, int numberOfRecords);

    List<User> showLimitUsersByBlockedStatus(int from, int numberOfRecords, boolean isBlocked);

    List<User> showBlockedUsers();

    List<User> showUnblockedUsers();

    int showCountOfUsers();

    int showCountOfUsersByBlockedStatus(boolean statusBlocked);

    boolean blockUser(int id) throws SQLException, NamingException, ClassNotFoundException;

    boolean unblockUser(int id) throws SQLException, NamingException, ClassNotFoundException;
}
