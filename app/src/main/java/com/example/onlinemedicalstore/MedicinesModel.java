package com.example.onlinemedicalstore;

import java.io.Serializable;

public class MedicinesModel implements Serializable {

    private String medicineId, categoryId, image, description, discount, price, name;


  /*  public MedicinesModel(String name, String image, String medicineId, String categoryId, String description, String discount, String price) {
        this.medicineId = medicineId;
        this.categoryId = categoryId;
        this.image = image;
        this.description = description;
        this.discount = discount;
        this.price = price;
        this.name = name;
    }*/

    public MedicinesModel() {
    }

    public String getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(String medicineId) {
        this.medicineId = medicineId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDiscount() {
        return discount;
    }

    public void setDiscount(String discount) {
        this.discount = discount;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
