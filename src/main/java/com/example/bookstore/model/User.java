package com.example.bookstore.model;

public class User {
    private int id;

    private String fullName;
    private String username;
    private String email;
    private String phone;

    private String address;
    private String city;
    private String postalCode;

    private String favoriteGenre;
    private String role;

    private String password;

    public User(int id, String fullName, String username, String email, String phone,
                String address, String city, String postalCode,
                String favoriteGenre, String role, String password) {

        this.id = id;
        this.fullName = fullName;
        this.username = username;
        this.email = email;
        this.phone = phone;

        this.address = address;
        this.city = city;
        this.postalCode = postalCode;

        this.favoriteGenre = favoriteGenre;
        this.role = role != null ? role : "user";
        this.password = password;
    }

    public User(int id, String username, String password) {
        this(id, null, username, null, null,
                null, null, null,
                null, "user", password);
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getFullName() { return fullName; }
    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getFavoriteGenre() { return favoriteGenre; }
    public void setFavoriteGenre(String favoriteGenre) { this.favoriteGenre = favoriteGenre; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
