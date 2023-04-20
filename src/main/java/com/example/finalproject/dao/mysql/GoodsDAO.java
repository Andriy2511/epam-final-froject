package com.example.finalproject.dao.mysql;

import com.example.finalproject.dao.GenericDAO;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.Goods;
import com.example.finalproject.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class GoodsDAO extends GenericDAO<Goods> implements IGoodsDAO {
    private static final Logger logger = LogManager.getLogger(GoodsDAO.class);
    private GoodsDAO(){}
    private static GoodsDAO instance;
    public static IGoodsDAO getInstance() {
        if (instance == null)
            instance = new GoodsDAO();
        return instance;
    }

    @Override
    public List<Goods> showLimitGoods(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_LIMIT_GOODS)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> getGoodsById(int id){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_GOODS_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> showGoodsList(){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ALL_FROM_GOODS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByNameGrowth(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_NAME_GROWTH)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByNameDecrease(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_NAME_DECREASE)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByPriceGrowth(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_PRICE_GROWTH)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByPriceDecrease(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_PRICE_DECREASE)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByPublicationDateGrowth(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_PUBLICATION_DATE_GROWTH)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByPublicationDateDecrease(int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_PUBLICATION_DATE_DECREASE)) {
            preparedStatement.setInt(1, from);
            preparedStatement.setInt(2, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByPriceRangeGrowth(double lowerPrice, double higherPrice){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_PRICE_RANGE_GROWTH)) {
            preparedStatement.setDouble(1, lowerPrice);
            preparedStatement.setDouble(2, higherPrice);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByPriceRangeDecrease(double lowerPrice, double higherPrice){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_PRICE_RANGE_DECREASE)) {
            preparedStatement.setDouble(1, lowerPrice);
            preparedStatement.setDouble(2, higherPrice);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByCategoryGrowth(String categoryName, int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_CATEGORY_GROWTH)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.setInt(2, from);
            preparedStatement.setInt(3, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public List<Goods> sortGoodsByCategoryDecrease(String categoryName, int from, int numberOfRecords){
        List<Goods> goodsList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SORT_BY_CATEGORY_DECREASE)) {
            preparedStatement.setString(1, categoryName);
            preparedStatement.setInt(2, from);
            preparedStatement.setInt(3, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return goodsList;
    }

    @Override
    public int showCountOfGoods(){
        int count = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_NUMBER_OF_GOODS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                count = rs.getInt(1);
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return count;
    }

    @Override
    public boolean addGoods(Goods goods) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.INSERT_GOODS);
            mapFromEntity(preparedStatement, goods);
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
    public boolean deleteGoods(int id) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.DELETE_GOODS_BY_ID);
            preparedStatement.setInt(1, id);
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
    public boolean changeGoods(int id, String name, String description, String photo, Double price, int categoryId) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_GOODS);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, description);
            preparedStatement.setString(3, photo);
            preparedStatement.setDouble(4, price);
            preparedStatement.setInt(5, categoryId);
            preparedStatement.setInt(6, id);
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
    public boolean changeGoodsName(int id, String name) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_GOODS_NAME);
            preparedStatement.setString(1, name);
            preparedStatement.setInt(2, id);
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
    public boolean changeGoodsPrice(int id, Double price) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_GOODS_PRICE);
            preparedStatement.setDouble(1, price);
            preparedStatement.setInt(2, id);
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
    public boolean changeGoodsPhoto(int id, String photo) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_GOODS_PROTO);
            preparedStatement.setString(1, photo);
            preparedStatement.setInt(2, id);
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
    public boolean changeGoodsDescription(int id, String description) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_GOODS_DESCRIPTION);
            preparedStatement.setString(1, description);
            preparedStatement.setInt(2, id);
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
    public boolean changeGoodsCategory(int id, int categoryId) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_GOODS_CATEGORY);
            preparedStatement.setInt(1, categoryId);
            preparedStatement.setInt(2, id);
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
    public int getGoodsIdByName(String name) {
        List<Goods> goodsList = new ArrayList<>();
        int id = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_GOODS_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                goodsList.add(mapToEntity(rs));
            }
            id = goodsList.get(0).getId();
        } catch (SQLException | IndexOutOfBoundsException e){
            id = 0;
            logger.error(e);
        }
        return id;
    }

    @Override
    public int getCountOfGoodsByCategory(String categoryName) {
        int countOfGoods = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_COUNT_OF_GOODS_BY_CATEGORY_NAME)) {
            preparedStatement.setString(1, categoryName);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                countOfGoods = rs.getInt(1);
            }
        } catch (SQLException | IndexOutOfBoundsException e){
            countOfGoods = 0;
            logger.error(e);
        }
        return countOfGoods;
    }

    public static String getCurrentTime(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    @Override
    protected Goods mapToEntity(ResultSet rs) throws SQLException {
        return new Goods(rs.getInt("id"), rs.getString("name"), rs.getString("description"),
                rs.getString("photo"), rs.getDouble("price"), rs.getInt("categories_id"),
                rs.getString("publication_date"));
    }

    @Override
    protected void mapFromEntity(PreparedStatement preparedStatement, Goods goods) throws SQLException {
        preparedStatement.setString(1, goods.getName());
        preparedStatement.setString(2, goods.getDescription());
        preparedStatement.setString(3, goods.getPhoto());
        preparedStatement.setDouble(4, goods.getPrice());
        preparedStatement.setInt(5, goods.getCategoryId());
        preparedStatement.setString(6, getCurrentTime());
    }
}
