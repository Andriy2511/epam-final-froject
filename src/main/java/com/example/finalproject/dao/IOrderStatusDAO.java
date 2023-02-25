package com.example.finalproject.dao;

import com.example.finalproject.models.OrderStatus;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Contains OrderStatusDAO methods
 */
public interface IOrderStatusDAO {
    List<OrderStatus> getOrderStatusById(int id);

    boolean changeOrderStatus(int id, int statusId) throws SQLException, NamingException, ClassNotFoundException;
}
