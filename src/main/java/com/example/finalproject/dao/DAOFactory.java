package com.example.finalproject.dao;

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
}
