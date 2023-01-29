package com.example.finalproject.dao.mysql;

import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.models.Category;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryDAOTest {
    Category testCategory = new Category(1000, "testCategory");;
    ICategoryDAO categoryDAO = DAOFactory.getDaoFactory("MYSQL").getCategoryDAO();
    int id;

    public void init(){
        id = categoryDAO.showCategories().size();
    }

    @Test
    void showCategories() {
        System.out.println(categoryDAO.showCategories());
    }

    @Test
    void showCategoryByName() {
        assertEquals(testCategory.getName(), categoryDAO.showCategoryById(1000).get(0).getName());
    }

    @Test
    void showCategoryById() {
        assertEquals(testCategory.getId(), categoryDAO.showCategoryById(1000).get(0).getId());
    }

    @Test
    void addCategory() {

    }

    @Test
    void deleteCategory() {
    }
}