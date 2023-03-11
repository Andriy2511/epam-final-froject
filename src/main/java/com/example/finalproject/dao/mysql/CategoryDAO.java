package com.example.finalproject.dao.mysql;

import com.example.finalproject.dao.GenericDAO;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.Category;
import com.example.finalproject.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryDAO extends GenericDAO<Category> implements ICategoryDAO {
    private static final Logger logger = LogManager.getLogger(CategoryDAO.class);
    private CategoryDAO(){}
    private static CategoryDAO instance;
    public static ICategoryDAO getInstance() {
        if (instance == null)
            instance = new CategoryDAO();
        return instance;
    }

    @Override
    public List<Category> showCategories(){
        List<Category> categoriesList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ALL_CATEGORIES)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                categoriesList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return categoriesList;
    }

    @Override
    public List<Category> showCategoryByName(String name){
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_CATEGORY_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                categoryList.add(mapToEntity(rs));
            }
        } catch (SQLException | RuntimeException  e){
            logger.error(e);
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public List<Category> showCategoryById(int categoryId){
        List<Category> categoryList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_CATEGORY_BY_ID)) {
            preparedStatement.setInt(1, categoryId);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                categoryList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public boolean addCategory(String name) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.INSERT_CATEGORY);
            preparedStatement.setString(1, name);
            result = preparedStatement.executeUpdate() == 1;
            connection.commit();
        } catch (SQLException e){
            logger.error(e);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return result;
    }

    @Override
    public boolean deleteCategory(String name) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.DELETE_CATEGORY);
            preparedStatement.setString(1, name);
            result = preparedStatement.executeUpdate() == 1;
            connection.commit();
        } catch (SQLException e){
            connection.rollback();
            logger.error(e);
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return result;
    }

    @Override
    protected Category mapToEntity(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    protected void mapFromEntity(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setInt(1, category.getId());
        preparedStatement.setString(2, category.getName());
    }
}
