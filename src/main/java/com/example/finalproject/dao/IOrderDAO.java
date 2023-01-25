package com.example.finalproject.dao;

import com.example.finalproject.models.Order;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface IOrderDAO {

    List<Order> showLimitOrders(int from, int numberOfRecords, String orderStatus);

    List<Order> showLimitOrdersByUser(int from, int numberOfRecords, int id);

    int showCountOfOrders(String orderName);

    int showCountOfOrdersByUserId(int userId);

    int selectOrderIdByName(String name);

    List<Order> getOrderById(int id);

    List<Order> showAllOrders();

    List<Order> showOrdersByUser(int id);

    List<Order> showOrdersByGoods(int id);

    List<Order> showOrdersByStatus(int id);

    boolean addNewOrder(int goodsId, int userId) throws SQLException, NamingException, ClassNotFoundException;
}
