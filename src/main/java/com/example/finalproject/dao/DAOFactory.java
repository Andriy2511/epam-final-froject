package com.example.finalproject.dao;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Abstract DAO factory
 */
public abstract class DAOFactory {

    /**
     * The method gets a name with database type and returns its instance
     * @param db type of the database
     * @return an instance of the database
     */
    public static DAOFactory getDaoFactory(String db) {
        switch (db) {
            case "MYSQL":
                return MySqlDAOFactory.getInstance();
            default:
                throw new IllegalArgumentException();
        }
    }

    public abstract IUserDAO getUserDAO();
    public abstract ICategoryDAO getCategoryDAO();
    public abstract IGoodsDAO getGoodsDAO();
    public abstract IOrderDAO getOrderDAO();
    public abstract IOrderStatusDAO getOrderStatusDAO();
    public abstract IRoleDAO getRoleDAO();


    public void commit(Connection connection) {
        try {
            connection.commit();
        } catch (SQLException e) {
            // log
        }
    }

    public void rollback(Connection connection) {
        try {
            connection.rollback();
        } catch (SQLException ex) {
            //Logger
            System.out.println("rollback failed");
        }
    }

    public void endTransaction(Connection connection) {
        try {
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            //Logger
        }
        close(connection);
    }

    public void close(Connection con) {
        if (con != null)
            try {
                con.close();
            } catch (SQLException e) {
                //Logger
            }
    }
}
