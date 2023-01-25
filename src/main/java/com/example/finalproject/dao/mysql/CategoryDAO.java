package com.example.finalproject.dao.mysql;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.GenericDAO;
import com.example.finalproject.dao.ICategoryDAO;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.Category;
import com.example.finalproject.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

//https://stackoverflow.com/questions/15761791/transaction-rollback-on-sqlexception-using-new-try-with-resources-block
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
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            logger.error(e);
            throw new RuntimeException(e);
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
        } catch (SQLException | ClassNotFoundException | RuntimeException | NamingException e){
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
        } catch (SQLException | ClassNotFoundException | NamingException e){
            logger.error(e);
            e.printStackTrace();
        }
        return categoryList;
    }

    @Override
    public boolean addCategory(String name){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection()){
            connection.setAutoCommit(false);
            try(PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.INSERT_CATEGORY)) {
                preparedStatement.setString(1, name);
                result = preparedStatement.executeUpdate() == 1;
            } catch (SQLException e){
                connection.rollback();
                connection.setAutoCommit(false);
                e.printStackTrace();
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException | ClassNotFoundException | NamingException e){
            logger.error(e);
            e.printStackTrace();
        }
        return result;
    }

    @Override
    public boolean deleteCategory(String name){
        boolean result = false;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.DELETE_CATEGORY)) {
            preparedStatement.setString(1, name);
            result = preparedStatement.executeUpdate() == 1;
        } catch (SQLException | ClassNotFoundException e){
            logger.error(e);
            e.printStackTrace();
        } catch (NamingException e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    @Override
    protected Category mapToEntity(ResultSet rs) throws SQLException {
        return new Category(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    protected PreparedStatement mapFromEntity(PreparedStatement preparedStatement, Category category) throws SQLException {
        preparedStatement.setInt(1, category.getId());
        preparedStatement.setString(2, category.getName());
        return preparedStatement;
    }
}
