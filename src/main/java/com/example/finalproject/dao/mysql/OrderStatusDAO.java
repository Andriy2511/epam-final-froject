package com.example.finalproject.dao.mysql;

import com.example.finalproject.command.admin.AddProductCommand;
import com.example.finalproject.dao.GenericDAO;
import com.example.finalproject.dao.IOrderStatusDAO;
import com.example.finalproject.dao.query.DBQuery;
import com.example.finalproject.models.OrderStatus;
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

public class OrderStatusDAO extends GenericDAO<OrderStatus> implements IOrderStatusDAO {
    private static final Logger logger = LogManager.getLogger(OrderStatusDAO.class);
    private OrderStatusDAO(){}
    private static OrderStatusDAO instance;
    public static IOrderStatusDAO getInstance() {
        if (instance == null)
            instance = new OrderStatusDAO();
        return instance;
    }

    @Override
    public List<OrderStatus> getOrderStatusById(int id){
        List<OrderStatus> orderStatusList = new ArrayList<>();
        try(Connection connection = JDBCUtils.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.SELECT_ORDER_STATUS_BY_ID)) {
            preparedStatement.setInt(1, id);
            ResultSet rs = preparedStatement.executeQuery();
            while (rs.next()){
                orderStatusList.add(mapToEntity(rs));
            }
        } catch (SQLException | ClassNotFoundException | NamingException e){
            logger.error(e);
            e.printStackTrace();
        }
        return orderStatusList;
    }
    @Override
    public boolean changeOrderStatus(int id, int statusId) throws SQLException, NamingException, ClassNotFoundException {
        boolean result = false;
        Connection connection = JDBCUtils.getConnection();
        try {
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(DBQuery.UPDATE_ORDER_STATUS);
            preparedStatement.setInt(1, statusId);
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
    protected OrderStatus mapToEntity(ResultSet rs) throws SQLException {
        return new OrderStatus(rs.getInt("id"), rs.getString("name"));
    }

    @Override
    protected PreparedStatement mapFromEntity(PreparedStatement preparedStatement, OrderStatus orderStatus) throws SQLException {
        preparedStatement.setInt(1, orderStatus.getId());
        preparedStatement.setString(2, orderStatus.getName());
        return preparedStatement;
    }
}
