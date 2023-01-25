package com.example.finalproject.dao;

import com.example.finalproject.models.Login;
import com.example.finalproject.models.Order;
import com.example.finalproject.models.User;

import java.util.List;

public interface IUserDAO {
    boolean validate(Login login) throws ClassNotFoundException;

    int getUserId(Login loginBean);

    boolean createUser(User user);

    boolean createOrder(int userId, int goodsId);

    List<Order> showOrdersByUser(int id);

    boolean updateName(int id, String name);

    boolean updateSurname(int id, String surname);

    boolean updateLogin(int id, String login);

    boolean updatePassword(int id, String password);

    boolean updateEmail(int id, String email);

    int getRoleIdUser();

    int getOrderStatusIdRegistered();

    //TODO
    List<User> getUserById(Integer id);

    boolean updateUser(User user);

    boolean deleteUser(User user);

    boolean deleteUserById(int id);

    List<User> readUserByLogin(String login);

    List<User> selectAllUsers();

    List<User> showAllUsers();

    List<User> showLimitUsers(int from, int numberOfRecords);

    List<User> showLimitUsersByBlockedStatus(int from, int numberOfRecords, boolean isBlocked);

    List<User> showBlockedUsers();

    List<User> showUnblockedUsers();

    int showCountOfUsers();

    int showCountOfUsersByBlockedStatus(boolean statusBlocked);

    boolean blockUser(int id);

    boolean unblockUser(int id);
}
