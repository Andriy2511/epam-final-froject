package com.example.finalproject.dao;

import com.example.finalproject.models.Goods;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

public interface IGoodsDAO {
    List<Goods> showLimitGoods(int from, int numberOfRecords);

    List<Goods> getGoodsById(int id);

    List<Goods> showGoodsList();

//    List<Goods> sortGoodsByNameGrowth();

    List<Goods> sortGoodsByNameGrowth(int from, int numberOfRecords);

    List<Goods> sortGoodsByNameDecrease();

//    List<Goods> sortGoodsByPriceGrowth();

    List<Goods> sortGoodsByPriceGrowth(int from, int numberOfRecords);

    List<Goods> sortGoodsByPriceDecrease();

    List<Goods> sortGoodsByPublicationDateGrowth();

//    List<Goods> sortGoodsByPublicationDateDecrease();

    List<Goods> sortGoodsByPublicationDateDecrease(int from, int numberOfRecords);

    List<Goods> sortGoodsByPriceRangeGrowth(double lowerPrice, double higherPrice);

    List<Goods> sortGoodsByPriceRangeDecrease(double lowerPrice, double higherPrice);

//    List<Goods> sortGoodsByCategory(int id);

    List<Goods> sortGoodsByCategory(int id, int from, int numberOfRecords);

    int showCountOfGoods();

    boolean addGoods(Goods goods) throws SQLException, NamingException, ClassNotFoundException;

    boolean deleteGoods(int id) throws SQLException, NamingException, ClassNotFoundException;

    boolean changeGoods(int id, String name, String description, String photo, Double price, int categoryId) throws SQLException, NamingException, ClassNotFoundException;

    boolean changeGoodsName(int id, String name) throws SQLException, NamingException, ClassNotFoundException;

    boolean changeGoodsPrice(int id, Double price) throws SQLException, NamingException, ClassNotFoundException;

    boolean changeGoodsPhoto(int id, String photo) throws SQLException, NamingException, ClassNotFoundException;

    boolean changeGoodsDescription(int id, String description) throws SQLException, NamingException, ClassNotFoundException;

    boolean changeGoodsCategory(int id, int categoryId) throws SQLException, NamingException, ClassNotFoundException;
}
