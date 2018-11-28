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
    private String password;
    private String city;
    private String zip;


    public Customer() {
        id = customerTotalNumber+1;
        customerTotalNumber = id;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
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

    public void setId(int id) {
        this.id = id;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }
}
