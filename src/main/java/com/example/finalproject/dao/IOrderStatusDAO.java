package com.example.finalproject.dao;

import com.example.finalproject.models.OrderStatus;
import java.sql.SQLException;
import java.util.List;

/**
 * Contains OrderStatusDAO methods
 */
public interface IOrderStatusDAO {
    /**
     * Gets the status of the order by id
     * @param id order status id
     * @return a list of order statuses
     */
    List<OrderStatus> getOrderStatusById(int id);

    /**
     * Changes the status of the order by id
     * @param id order id
     * @param statusId order status id
     * @return the result of successful or unsuccessful ending
     */
    boolean changeOrderStatus(int id, int statusId) throws SQLException;

    /**
     * Shows the order status id
     * @return order status id
     */
    int getOrderStatusIdRegistered();

    /**
     * Adds new order status
     * @param name order name
     * @return the result of successful or unsuccessful ending
     */
    boolean addNewOrderStatus(String name) throws SQLException;
}