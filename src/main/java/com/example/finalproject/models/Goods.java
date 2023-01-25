package com.example.finalproject.models;

import com.example.finalproject.dao.DAOFactory;

public class Goods {
    private int id;
    private String name;
    private String description;
    private String photo;
    private double price;
    private int categoryId;
    private String publicationTime;

    public Goods() {
    }

    public Goods(String name, String description, String photo, double price, int categoryId) {
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Goods(int id, String name, String description, String photo, double price, int categoryId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.categoryId = categoryId;
    }

    public Goods(int id, String name, String description, String photo, double price, int categoryId, String publicationTime) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.photo = photo;
        this.price = price;
        this.categoryId = categoryId;
        this.publicationTime = publicationTime;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(int categoryId) {
        this.categoryId = categoryId;
    }

    public String getPublicationTime() {
        return publicationTime;
    }

    public void setPublicationTime(String publicationTime) {
        this.publicationTime = publicationTime;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", photo='" + photo + '\'' +
                ", price=" + price +
                ", categoryId=" + categoryId +
                ", publicationTime='" + publicationTime + '\'' +
                '}';
    }
}
