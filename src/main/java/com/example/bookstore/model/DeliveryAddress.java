package com.example.bookstore.model;

public class DeliveryAddress {
    private String label;
    private String address;
    private String city;
    private String postalCode;

    public DeliveryAddress() {
    }

    public DeliveryAddress(String label, String address, String city, String postalCode) {
        this.label = label;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public String toString() {
        return (label != null && !label.isEmpty() ? label + ": " : "") + address + ", " + city + " " + postalCode;
    }
}
