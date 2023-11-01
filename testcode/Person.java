package com.stardevllc.stardata.mysql.test;

import com.stardevllc.stardata.api.annotations.ForeignKeyStorage;
import com.stardevllc.stardata.api.annotations.Name;
import com.stardevllc.stardata.api.annotations.Order;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("MismatchedQueryAndUpdateOfCollection")
@Name("people")
public class Person {
    @Order(1) private long id;
    @Order(2) private String firstName;
    @Order(3) private String lastName;
    @Order(4) private int age;
    
    @ForeignKeyStorage(clazz = Address.class, field = "personid")
    private Address address;
    
    @ForeignKeyStorage(clazz = Transaction.class, field = "personid")
    private List<Transaction> transactions = new ArrayList<>();

    public Person(String firstName, String lastName, int age, Address address, List<Transaction> transactions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.address = address;
        this.transactions.addAll(transactions);
    }
    
    private Person() {}

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Person{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", address=" + address +
                ", transactions=" + transactions +
                '}';
    }
}