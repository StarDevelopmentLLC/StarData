package com.stardevllc.stardata.mysql.test;

import com.stardevllc.stardata.api.FKAction;
import com.stardevllc.stardata.api.annotations.*;

@Name("transactions")
public class Transaction {
    @Order(1) private long id;
    @Order(2) private int amount;
    @Order(3) private long timestamp;
    
    @ForeignKey(Person.class)
    @FKOnUpdate(FKAction.CASCADE)
    @FKOnDelete(FKAction.CASCADE)
    @Order(4) private long personid;

    public Transaction(int amount, long timestamp) {
        this.amount = amount;
        this.timestamp = timestamp;
    }

    private Transaction() {}

    public long getId() {
        return id;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public long getPersonid() {
        return personid;
    }

    public void setPersonid(long personid) {
        this.personid = personid;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "id=" + id +
                ", amount=" + amount +
                ", timestamp=" + timestamp +
                ", personid=" + personid +
                '}';
    }
}