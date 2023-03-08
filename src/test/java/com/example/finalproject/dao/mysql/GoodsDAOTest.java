package com.example.finalproject.dao.mysql;

import com.example.finalproject.dao.DAOFactory;
import com.example.finalproject.dao.IGoodsDAO;
import com.example.finalproject.models.Goods;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.Properties;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class GoodsDAOTest {

    @BeforeAll
    public static void setUp() {
        System.setProperty(Context.INITIAL_CONTEXT_FACTORY, "org.apache.naming.java.javaURLContextFactory");
    }
    DAOFactory daoFactory  = DAOFactory.getDaoFactory("MYSQL");
    IGoodsDAO goodsDAO =  daoFactory.getGoodsDAO();
    @Test
    void showLimitGoods() {
    }

    @Test
    void getGoodsById() {
    }

    @Test
    void showGoodsList() {
    }

    @Test
    void sortGoodsByNameGrowth() {
    }

    @Test
    void sortGoodsByNameDecrease() {
    }

    @Test
    void sortGoodsByPriceGrowth() {
    }

    @Test
    void sortGoodsByPriceDecrease() {
    }

    @Test
    void sortGoodsByPublicationDateGrowth() {
    }

    @Test
    void sortGoodsByPublicationDateDecrease() {
    }

    @Test
    void sortGoodsByPriceRangeGrowth() {
    }

    @Test
    void sortGoodsByPriceRangeDecrease() {
    }

    @Test
    void sortGoodsByCategory() {
    }

    @Test
    void showCountOfGoods() {
    }

    @Test
    void testAddGoods() throws SQLException, NamingException, ClassNotFoundException {
        Goods goods = new Goods(100, "test", "test", null, 100, 10, null);
        goodsDAO.addGoods(goods);
    }

    @Test
    void deleteGoods() {
    }

    @Test
    void changeGoods() {
    }

    @Test
    void changeGoodsName() {
    }

    @Test
    void changeGoodsPrice() {
    }

    @Test
    void changeGoodsPhoto() {
    }

    @Test
    void changeGoodsDescription() {
    }

    @Test
    void changeGoodsCategory() {
    }
}