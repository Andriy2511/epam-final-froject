package com.example.finalproject.dao;

import com.example.finalproject.models.Order;

import java.sql.SQLException;
import java.util.List;

/**
 * Contains OrderDAO methods
 */
public interface IOrderDAO {

    /**
     * Shows a limited number of orders
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @param orderStatus order status
     * @return list with orders
     */
    List<Order> showLimitOrders(int from, int numberOfRecords, String orderStatus);

    /**
     * Shows a limited number of orders by user id
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @param id user id
     * @return list with orders
     */
    List<Order> showLimitOrdersByUser(int from, int numberOfRecords, int id);

    /**
     * Shows orders by name
     * @param orderName order name
     * @return integer value with number of orders
     */
    int showCountOfOrders(String orderName);

    /**
     * Shows orders by user id
     * @param userId user id
     * @return integer value with number of orders
     */
    int showCountOfOrdersByUserId(int userId);

    /**
     * Shows an order id by name
     * @param name order name
     * @return integer value with number of orders
     */
    int selectOrderIdByName(String name);

    /**
     * Shows orders
     * @param id order id
     * @return list with orders
     */
    List<Order> getOrderById(int id);

    /**
     * Shows all orders
     * @return list with orders
     */
    List<Order> showAllOrders();

    /**
     * Shows orders by user
     * @param id user id
     * @return list with orders
     */
    List<Order> showOrdersByUser(int id);

    /**
     * Shows orders by goods
     * @param id goods id
     * @return list with orders
     */
    List<Order> showOrdersByGoods(int id);

    /**
     * Shows orders by status
     * @param id status id
     * @return list with orders
     */
    List<Order> showOrdersByStatus(int id);

    /**
     * Adds a new order
     * @param goodsId goods id
     * @param userId user id
     * @return id of added order
     */
    int addNewOrder(int goodsId, int userId) throws SQLException;

    /**
     * Deletes order by id
     * @param id order id
     * @return the result of successful or unsuccessful ending
     */
    boolean deleteOrder(int id) throws SQLException;
}
