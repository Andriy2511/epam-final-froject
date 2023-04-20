package com.example.finalproject.models;

import java.util.Objects;

public class Order {
    private int id;
    private int goodsId;
    private int userId;
    private int orderStatusId;

    public Order() {
    }

    public Order(int id, int goodsId, int userId, int orderStatusId) {
        this.id = id;
        this.goodsId = goodsId;
        this.userId = userId;
        this.orderStatusId = orderStatusId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(int goodsId) {
        this.goodsId = goodsId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(int orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", userId=" + userId +
                ", orderStatusId=" + orderStatusId +
                '}';
    }
}
