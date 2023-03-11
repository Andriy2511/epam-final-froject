package com.example.finalproject.dao.mysql;

import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IOrderDAO;
import org.junit.jupiter.api.Test;

import javax.naming.NamingException;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class OrderDAOTest {

    private static final IOrderDAO orderDAO = DAOFactory.getDaoFactory("MYSQL").getOrderDAO();
    @Test
    void showLimitOrders() {
    }

    @Test
    void showLimitOrdersByUser() {
    }

    @Test
    void showCountOfOrders() {
    }

    @Test
    void showCountOfOrdersByUserId() {
    }

    @Test
    void selectOrderIdByName() {
    }

    @Test
    void getOrderById() {
    }

    @Test
    void showAllOrders() {
    }

    @Test
    void showOrdersByUser() {
    }

    @Test
    void showOrdersByGoods() {
    }

    @Test
    void showOrdersByStatus() {
    }

    @Test
    void addNewOrder() throws SQLException, NamingException, ClassNotFoundException {
        orderDAO.addNewOrder(133, 77);
    }

    @Test
    void mapToEntity() {
    }

    @Test
    void mapFromEntity() {
    }
}