package com.example.aplikasicrudsqlite.models;

public class Inventory {
    private long id;
    private String name;
    private String brand;
    private String price;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Inventory " +
                "name = " + name +
                ", brand = " + brand +
                ", price = " + price;
    }


    public Inventory() {
    }
}
