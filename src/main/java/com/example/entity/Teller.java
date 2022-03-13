package com.example.entity;

import java.util.List;

/**
 * Teller class serves clients
 * @author Alexanrd Spaskin
 */
public class Teller implements Runnable {

    private BankCashDesk bankCashDesk;
    private List<Customer> customers; // The class receives one of N queues
    private Customer customer;

    public Teller(BankCashDesk bankCashDesk, List<Customer> customers) {
        this.bankCashDesk = bankCashDesk;
        this.customers = customers;
    }

    @Override
    public void run() {
        while (true) {
            serves();
        }
    }

    private void serves(){
        synchronized (customers) {
            if (customers.size() == 0) {
                try {
                    System.out.println(Thread.currentThread().getName() + " wait");
                    customers.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

        customer = customers.get(0);
        if (customer.isTypeOfOperation()) {
            System.out.println(Thread.currentThread().getName() + " replenished: " + customer.getServiceTime() + " second");
            sleep(customer.getServiceTime()); // Thread sleeps on getTransactionAmount
            bankCashDesk.cashReplenishment(customer.getTransactionAmount());
            System.out.println("\n" + bankCashDesk + "\n");
        } else {
            System.out.println(Thread.currentThread().getName() + " withdrawal: " + customer.getServiceTime() + " second");
            sleep(customer.getServiceTime()); // Thread sleeps on getTransactionAmount
            bankCashDesk.cashWithdrawal(customer.getTransactionAmount());
            System.out.println("\n" + bankCashDesk + "\n");
        }

        synchronized (customers){ // remove customer from queue after service
            customers.remove(0);
        }
    }

    private void sleep(int time){
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
