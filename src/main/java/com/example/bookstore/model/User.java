package com.example.bookstore.model;

public class User {
    private int id;

    private String fullName;
    private String username;
    private String email;
    private String phone;

    private String favoriteGenre;
    private String role;

    private String password;
    private int balance;
    private java.util.List<DeliveryAddress> deliveryAddresses;

    public User(int id, String fullName, String username, String email, String phone,
            String favoriteGenre, String role, String password, int balance) {

        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;

        this.favoriteGenre = favoriteGenre;
        this.role = role != null ? role : "user";
        this.password = password;
        this.balance = balance;
        this.deliveryAddresses = new java.util.ArrayList<>();
    }

    public User(int id, String username, String password) {
        this(id, null, username, null, null,
                null, "user", password, 0);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getFavoriteGenre() {
        return favoriteGenre;
    }

    public void setFavoriteGenre(String favoriteGenre) {
        this.favoriteGenre = favoriteGenre;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getBalance() {
        return balance;
    }

    public void setBalance(int balance) {
        this.balance = balance;
    }

    public java.util.List<DeliveryAddress> getDeliveryAddresses() {
        if (deliveryAddresses == null) {
            deliveryAddresses = new java.util.ArrayList<>();
        }
        return deliveryAddresses;
    }

    public void setDeliveryAddresses(java.util.List<DeliveryAddress> deliveryAddresses) {
        this.deliveryAddresses = deliveryAddresses;
    }

    public void addDeliveryAddress(DeliveryAddress address) {
        getDeliveryAddresses().add(address);
    }
}
