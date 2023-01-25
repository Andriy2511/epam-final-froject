package com.example.finalproject.dao;

import com.example.finalproject.models.Goods;

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

    int showCountOfGoodsByUser();

    boolean addGoods(Goods goods);

    boolean deleteGoods(int id);

    boolean changeGoods(int id, String name, String description, String photo, Double price, int categoryId);

    boolean changeGoodsName(int id, String name);

    boolean changeGoodsPrice(int id, Double price);

    boolean changeGoodsPhoto(int id, String photo);

    boolean changeGoodsDescription(int id, String description);

    boolean changeGoodsCategory(int id, int categoryId);
}
