package com.example.finalproject.dao;

import java.sql.SQLException;

/**
 * Contains RoleDAO methods
 */
public interface IRoleDAO {
    /**
     * Receives admin id
     * @return the integer value of the administrator id
     */
    int getAdminId();

    /**
     * Receives user id
     * @return the integer value of the user id
     */
    int getUserId();

    /**
     * Adds a new role
     * @param role role name
     * @return the result of successfully or unsuccessfully adding a role
     */
    boolean addNewRole(String role) throws SQLException;
}
