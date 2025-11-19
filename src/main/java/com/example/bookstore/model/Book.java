    package com.example.bookstore.model;

    public class Book {

        private int id;
        private String title;
        private String author;
        private int price;
        private int stock;
        private String imgPath;

        public Book(String title, String author, int price, int stock, String imgPath) {
            this.title = title;
            this.author = author;
            this.price = price;
            this.stock = stock;
            this.imgPath = imgPath;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getImgPath() {
            return this.imgPath;
        }

        public void setImgPath(String imgPath) {
            this.imgPath = imgPath;
        }

        public String getAuthor() {
            return author;
        }

        public void setAuthor(String author) {
            this.author = author;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getStock() {
            return stock;
        }

        public void setStock(int stock) {
            this.stock = stock;
        }

        @Override
        public String toString() {
            return title + " - " + author;
        }
    }
