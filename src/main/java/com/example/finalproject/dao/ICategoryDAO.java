package com.example.finalproject.dao;

import com.example.finalproject.models.Category;

import java.util.List;

public interface ICategoryDAO {
    List<Category> showCategories();

    List<Category> showCategoryByName(String name);

    List<Category> showCategoryById(int categoryId);

    boolean addCategory(String name);

    boolean deleteCategory(String name);
}
