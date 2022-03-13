package com.example.generator;

import com.example.entity.Customer;
import java.util.List;

/**
 * Generator generation class and adding customer
 * @author Alexanrd Spaskin
 */
public class Generator implements Runnable {
    private Customer customer;
    private List<List<Customer>> queueList;
    private int SERVICE_TIME;
    private int CLIENTS_PER_MINUTE;

    public Generator(List<List<Customer>> queueList, int SERVICE_TIME, int CLIENTS_PER_MINUTE) {
        this.queueList = queueList;
        this.SERVICE_TIME = SERVICE_TIME;
        this.CLIENTS_PER_MINUTE = CLIENTS_PER_MINUTE;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(60000 / CLIENTS_PER_MINUTE);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            adding();
        }
    }
    /**
     * add method
     */
    private void adding(){
        generation();
        // search minimum queue
        int min = queueList.get(0).size();
        for (List<Customer> item : queueList) {
            if (item.size() < min) {
                min = item.size();
            }
        }
        for (int i = 0; i < queueList.size(); i++) {
            if (queueList.get(i).size() == min) {
                synchronized (queueList.get(i)) { // add customer
                    queueList.get(i).add(customer);
                    System.out.println("\nadd queue "+ i + " " + customer);
                    queueList.get(i).notify();
                }
                break;
            }
        }
    }

    /**
     * generation method
     */
    private void generation(){
        boolean type = ( Math.random() * (2 + 1) - 1 > 0); // [-1, 1] to boolean
        int amount = (int) (Math.random() * (1000 + 1)); // [0, 1000]
        int time = (int) (1000 + Math.random() * SERVICE_TIME * 1000); // [1, SERVICE_TIME * 1000) seconds
        this.customer = new Customer(type, amount, time);
    }
}
