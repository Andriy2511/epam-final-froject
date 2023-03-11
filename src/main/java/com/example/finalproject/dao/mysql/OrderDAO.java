package com.example.finalproject.dao.mysql;

import com.example.finalproject.dao.*;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.Order;
import com.example.finalproject.utils.JDBCUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.naming.NamingException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAO extends GenericDAO<Order> implements IOrderDAO {
    private static final Logger logger = LogManager.getLogger(OrderDAO.class);
    private OrderDAO(){}
    private static OrderDAO instance;
    public static IOrderDAO getInstance() {
        if (instance == null)
            instance = new OrderDAO();
        return instance;
    }

    DAOFactory daoFactory = DAOFactory.getDaoFactory("MYSQL");
    IOrderStatusDAO orderStatusDAO = daoFactory.getOrderStatusDAO();

    @Override
    public List<Order> showLimitOrders(int from, int numberOfRecords, String orderStatus){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_LIMIT_ORDERS)) {
            preparedStatement.setInt(1, selectOrderIdByName(orderStatus));
            preparedStatement.setInt(2, from);
            preparedStatement.setInt(3, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public List<Order> showLimitOrdersByUser(int from, int numberOfRecords, int id){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_LIMIT_ORDERS_BY_USER_ID)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setInt(2, from);
            preparedStatement.setInt(3, numberOfRecords);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public int showCountOfOrders(String orderName){
        int count = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_NUMBER_OF_ORDERS)) {
            preparedStatement.setInt(1, selectOrderIdByName(orderName));
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
    public int showCountOfOrdersByUserId(int userId){
        int count = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_NUMBER_OF_ORDERS_BY_USER)) {
            preparedStatement.setInt(1, userId);
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
    public int selectOrderIdByName(String name){
        int id = 0;
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_ID_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                id = rs.getInt(1);
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return id;
    }

    @Override
    public List<Order> getOrderById(int id){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public List<Order> showAllOrders(){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ALL_FROM_ORDERS)) {
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public List<Order> showOrdersByUser(int id){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_BY_USER)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public List<Order> showOrdersByGoods(int id){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_BY_GOODS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public List<Order> showOrdersByStatus(int id){
        List<Order> ordersList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_BY_STATUS)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                ordersList.add(mapToEntity(rs));
            }
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
        }
        return ordersList;
    }

    @Override
    public int addNewOrder(int goodsId, int userId) throws SQLException {
        int id = 0;
        Connection connection = JDBCUtils.getConnection();
        try{
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.INSERT_ORDER, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, goodsId);
            preparedStatement.setInt(2, userId);
            preparedStatement.setInt(3, orderStatusDAO.getOrderStatusIdRegistered());
            preparedStatement.executeUpdate();
            ResultSet rs = preparedStatement.getGeneratedKeys();
            while (rs.next()) {
                java.math.BigDecimal idColVar = rs.getBigDecimal(1);
                id = idColVar.intValue();
            }
            connection.commit();
        } catch (SQLException e){
            System.out.println("exception");
            e.printStackTrace();
            logger.error(e);
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return id;
    }

    @Override
    public boolean deleteOrder(int id) throws SQLException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.DELETE_ORDER_BY_ID);
            preparedStatement.setInt(1, id);
            result = preparedStatement.executeUpdate() == 1;
            connection.commit();
        } catch (SQLException e){
            logger.error(e);
            e.printStackTrace();
            connection.rollback();
        } finally {
            connection.setAutoCommit(true);
            connection.close();
        }
        return result;
    }

    @Override
    protected Order mapToEntity(ResultSet rs) throws SQLException {
        return new Order(rs.getInt("id"), rs.getInt("goods_id"),
                rs.getInt("users_id"), rs.getInt("orders_status_id"));
    }

    @Override
    protected void mapFromEntity(PreparedStatement preparedStatement, Order order) throws SQLException {
        preparedStatement.setInt(1, order.getId());
        preparedStatement.setInt(2, order.getGoodsId());
        preparedStatement.setInt(3, order.getUserId());
        preparedStatement.setInt(4, order.getOrderStatusId());
    }
}
