package com.example.entity;

/**
 * @author Alexanrd Spaskin
 */
public class Customer {

    private boolean typeOfOperation;
    private int transactionAmount;
    private int serviceTime;

    public Customer(boolean typeOfOperation, int transactionAmount, int serviceTime) {
        this.typeOfOperation = typeOfOperation;
        this.transactionAmount = transactionAmount;
        this.serviceTime = serviceTime;
    }

    public boolean isTypeOfOperation() {
        return typeOfOperation;
    }

    public int getTransactionAmount() {
        return transactionAmount;
    }

    public int getServiceTime() {
        return serviceTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "typeOfOperation=" + typeOfOperation +
                ", transactionAmount=" + transactionAmount +
                ", serviceTime=" + serviceTime +
                '}';
    }
}
