package com.example.finalproject.dao;

import com.example.finalproject.models.User;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Contains UserDAO methods
 */
public interface IUserDAO {
    /**
     * Checks if the user exists
     * @param user user login
     * @return authorization result
     */
    boolean validate(User user) throws ClassNotFoundException;

    /**
     * Shows user id
     * @param user user object
     * @return user id
     */
    int getUserId(User user) throws NamingException, ClassNotFoundException;

    /**
     * Creates user
     * @param user object of type "User" that should be added
     * @return the result of successful or unsuccessful ending
     */
    boolean createUser(User user) throws SQLException;

    /**
     * Updates name
     * @param id user id
     * @param name username
     * @return the result of successful or unsuccessful ending
     */
    boolean updateName(int id, String name) throws SQLException;

    /**
     * updates surname
     * @param id user id
     * @param surname user surname
     * @return the result of successful or unsuccessful ending
     */
    boolean updateSurname(int id, String surname) throws SQLException;

    /**
     * updates login
     * @param id user id
     * @param login user login
     * @return the result of successful or unsuccessful ending
     */
    boolean updateLogin(int id, String login) throws SQLException;

    /**
     * Updates password
     * @param id user id
     * @param password user password
     * @return the result of successful or unsuccessful ending
     */
    boolean updatePassword(int id, String password) throws SQLException;

    /**
     * Updates email
     * @param id user id
     * @param email user email
     * @return the result of successful or unsuccessful ending
     */
    boolean updateEmail(int id, String email) throws SQLException;

    /**
     * Shows user role id
     * @return user role id
     */
    int getRoleIdUser();

    /**
     * Shows user by id
     * @param id user id
     * @return list of users
     */
    List<User> getUserById(Integer id);

    /**
     * Updates the user by login
     * @param user object with type of "User" that should be added
     * @return the result of successful or unsuccessful ending
     */
    boolean updateUser(User user) throws SQLException;

    /**
     * Deletes the user
     * @param login login of user that should be added
     * @return the result of successful or unsuccessful ending
     */
    boolean deleteUser(String login) throws SQLException;

    /**
     * Deletes the user by login
     * @param id user id
     * @return the result of successful or unsuccessful ending
     */
    boolean deleteUserById(int id) throws SQLException;

    /**
     * Shows the user by login
     * @param login user login
     * @return list of users
     */
    List<User> readUserByLogin(String login);

    /**
     * Shows a list of users
     * @return list of users
     */
    List<User> showAllUsers();

    /**
     * Shows a limited list of users
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @return list of users
     */
    List<User> showLimitUsers(int from, int numberOfRecords);

    /**
     * Shows a limited list of blocked or unblocked users
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @param isBlocked blocked status
     * @return list of users
     */
    List<User> showLimitUsersByBlockedStatus(int from, int numberOfRecords, boolean isBlocked);

    /**
     * Shows blocked users
     * @return list of users
     */
    List<User> showBlockedUsers();

    /**
     * Shows unblocked users
     * @return list of users
     */
    List<User> showUnblockedUsers();

    /**
     * Shows the number of users
     * @return count of users
     */
    int showCountOfUsers();

    /**
     * Shows the number of blocked users
     * @param statusBlocked blocked status
     * @return count of users
     */
    int showCountOfUsersByBlockedStatus(boolean statusBlocked);

    /**
     * Blocks the user
     * @param id user id
     * @return the result of successful or unsuccessful ending
     */
    boolean blockUser(int id) throws SQLException;

    /**
     * Unblocks the user
     * @param id user id
     * @return the result of successful or unsuccessful ending
     */
    boolean unblockUser(int id) throws SQLException;
}
