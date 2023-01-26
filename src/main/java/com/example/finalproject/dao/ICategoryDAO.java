package com.example.finalproject.dao;

import com.example.finalproject.models.Category;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface ICategoryDAO {
    List<Category> showCategories();

    List<Category> showCategoryByName(String name);

    List<Category> showCategoryById(int categoryId);

    boolean addCategory(String name) throws SQLException, NamingException, ClassNotFoundException;

    boolean deleteCategory(String name) throws SQLException, NamingException, ClassNotFoundException;
}
