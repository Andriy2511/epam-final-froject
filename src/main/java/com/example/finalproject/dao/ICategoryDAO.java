package com.example.finalproject.dao;

import com.example.finalproject.models.Category;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Contains CategoryDAO methods
 */
public interface ICategoryDAO {
    /**
     * Shows all categories
     * @return list with categories
     */
    List<Category> showCategories();
    /**
     * Shows all categories by name
     * @param name name of the category
     * @return list with categories
     */
    List<Category> showCategoryByName(String name);
    /**
     * Shows all categories by id
     * @param categoryId if of the category
     * @return list with categories
     */
    List<Category> showCategoryById(int categoryId);

    /**
     * Adds category with defined name
     * @param name name of the category
     * @return list with categories
     */
    boolean addCategory(String name) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Deletes category with defined name
     * @param name name of the category
     * @return the result of successful or unsuccessful ending
     */
    boolean deleteCategory(String name) throws SQLException, NamingException, ClassNotFoundException;
}