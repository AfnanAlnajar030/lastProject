package com.example.buyfresh;

import java.io.Serializable;

public class Farmer implements Serializable {
    private String name ;
    private String productName;
    private String weight;
    private String price;

    public Farmer() {
    }

    public Farmer(String name, String weight, String price, String productName) {
        this.name = name;
        this.productName = productName;
        this.price = price;
        this.weight = weight;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
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
