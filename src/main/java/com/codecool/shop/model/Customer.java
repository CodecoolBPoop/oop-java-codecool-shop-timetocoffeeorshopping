package com.codecool.shop.model;

public class Customer {
    private int id;
    private static int customerTotalNumber = 0;
    private String firstName;
    private String lastName;
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

    public String getFirstName(String firstname) {
        return firstName;
    }

    public String getLastName() {
        return lastName;
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

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
