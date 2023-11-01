package com.stardevllc.stardata.mysql.test;

import com.stardevllc.stardata.api.annotations.ForeignKey;
import com.stardevllc.stardata.api.annotations.Name;
import com.stardevllc.stardata.api.annotations.Order;

@Name("addresses")
public class Address {
    @Order(1) private long id;
    @Order(2) private int streetNumber;
    @Order(3) private String city;
    @Order(4) private String state;
    
    @ForeignKey(Person.class)
    @Order(5) private long personid;

    public Address(int streetNumber, String city, String state) {
        this.streetNumber = streetNumber;
        this.city = city;
        this.state = state;
    }
    
    private Address() {}

    public long getId() {
        return id;
    }

    public int getStreetNumber() {
        return streetNumber;
    }

    public void setStreetNumber(int streetNumber) {
        this.streetNumber = streetNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public long getPersonid() {
        return personid;
    }

    @Override
    public String toString() {
        return "Address{" +
                "id=" + id +
                ", streetNumber=" + streetNumber +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", personid=" + personid +
                '}';
    }
}
