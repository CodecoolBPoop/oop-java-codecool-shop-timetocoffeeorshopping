package com.codecool.shop.model;

public class Customer {
    private int id;
    private static int customerTotalNumber = 0;
    private String name;
    private String email;
    private int phone;
    private String adressBilling;
    private String adressShipping;

    public Customer() {
        id = customerTotalNumber+1;
        customerTotalNumber = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public int getPhone() {
        return phone;
    }

    public String getAdressBilling() {
        return adressBilling;
    }

    public String getAdressShipping() {
        return adressShipping;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(int phone) {
        this.phone = phone;
    }

    public void setAdressBilling(String adressBilling) {
        this.adressBilling = adressBilling;
    }

    public void setAdressShipping(String adressShipping) {
        this.adressShipping = adressShipping;
    }
}
