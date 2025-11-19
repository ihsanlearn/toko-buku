package com.example.bookstore.model;

public class Product {
    private String name;
    private int price;
    private String imagePath;

    public Product(String name, int price, String imagePath) {
        this.name = name;
        this.price = price;
        this.imagePath = imagePath;
    }

    public String getName() { return name; }
    public int getPrice() { return price; }
    public String getImagePath() { return imagePath; }
}
