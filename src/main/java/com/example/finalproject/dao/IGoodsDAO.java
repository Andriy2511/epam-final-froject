package com.example.finalproject.dao;

import com.example.finalproject.models.Goods;

import javax.naming.NamingException;
import java.sql.SQLException;
import java.util.List;

/**
 * Contains GoodsDAO methods
 */
public interface IGoodsDAO {
    /**
     * Receives a list of goods from a given range of values
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @return list with goods
     */
    List<Goods> showLimitGoods(int from, int numberOfRecords);

    /**
     * Receives goods by id
     * @param id goods id
     * @return list with goods
     */
    List<Goods> getGoodsById(int id);

    /**
     * Receives a full list of goods
     * @return list with goods
     */
    List<Goods> showGoodsList();

//    List<Goods> sortGoodsByNameGrowth();

    /**
     * Sort goods by growth
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @return list with goods
     */
    List<Goods> sortGoodsByNameGrowth(int from, int numberOfRecords);

    /**
     * Sort goods by name by descending list
     * @return list with goods
     */
    List<Goods> sortGoodsByNameDecrease();

//    List<Goods> sortGoodsByPriceGrowth();

    /**
     * Sort the goods by price by the growing list
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @return list with goods
     */
    List<Goods> sortGoodsByPriceGrowth(int from, int numberOfRecords);

    /**
     * Sort the goods by price by the descending list
     * @return list with goods
     */
    List<Goods> sortGoodsByPriceDecrease();

    /**
     * Sort the goods by the date of publication by the growing list
     * @return list with goods
     */
    List<Goods> sortGoodsByPublicationDateGrowth();

//    List<Goods> sortGoodsByPublicationDateDecrease();

    /**
     * Sort the goods by the date of publication by the descending list
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @return list with goods
     */
    List<Goods> sortGoodsByPublicationDateDecrease(int from, int numberOfRecords);

    /**
     * Sort goods by a limited price by the growing list
     * @param lowerPrice lower price
     * @param higherPrice higher price
     * @return list with goods
     */
    List<Goods> sortGoodsByPriceRangeGrowth(double lowerPrice, double higherPrice);

    /**
     * Sort goods by a limited price by the descending list
     * @param lowerPrice lower price
     * @param higherPrice higher price
     * @return list with goods
     */
    List<Goods> sortGoodsByPriceRangeDecrease(double lowerPrice, double higherPrice);

//    List<Goods> sortGoodsByCategory(int id);

    /**
     * Sort goods by category
     * @param id goods id
     * @param from initial entry
     * @param numberOfRecords the last entry
     * @return list with goods
     */
    List<Goods> sortGoodsByCategory(int id, int from, int numberOfRecords);

    /**
     * Counts the quantity of goods
     * @return integer value with number of goods
     */
    int showCountOfGoods();

    /**
     * Adds goods to the database
     * @param goods "Goods" type object
     * @return the result of successful or unsuccessful ending
     */
    boolean addGoods(Goods goods) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Removes the goods from database
     * @param id goods id
     * @return the result of successful or unsuccessful ending
     */
    boolean deleteGoods(int id) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Changes the goods in the database
     * @param id goods id
     * @param name goods name
     * @param description goods description
     * @param photo goods photo
     * @param price goods price
     * @param categoryId goods category
     * @return the result of successful or unsuccessful ending
     */
    boolean changeGoods(int id, String name, String description, String photo, Double price, int categoryId) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Changes the name of the goods
     * @param id goods id
     * @param name goods name
     * @return the result of successful or unsuccessful ending
     */
    boolean changeGoodsName(int id, String name) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Changes the price of the goods
     * @param id goods id
     * @param price goods price
     * @return the result of successful or unsuccessful ending
     */
    boolean changeGoodsPrice(int id, Double price) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Changes the photo of the goods
     * @param id goods id
     * @param photo goods photo
     * @return the result of successful or unsuccessful ending
     */
    boolean changeGoodsPhoto(int id, String photo) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Changes the description of the goods
     * @param id goods id
     * @param description goods description
     * @return the result of successful or unsuccessful ending
     */
    boolean changeGoodsDescription(int id, String description) throws SQLException, NamingException, ClassNotFoundException;

    /**
     * Changes the category of the goods
     * @param id goods id
     * @param categoryId category id
     * @return the result of successful or unsuccessful ending
     */
    boolean changeGoodsCategory(int id, int categoryId) throws SQLException, NamingException, ClassNotFoundException;
}
