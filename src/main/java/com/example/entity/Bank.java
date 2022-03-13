package com.example.entity;

import com.example.generator.Generator;

import java.util.*;

/**
 * The bank class creates a cash register, a generator and a tellers
 * @author Alexanrd Spaskin
 */
public class Bank {
    private static final int N = 3;
    private static final int SERVICE_TIME = 60; // Generator: [1, SERVICE_TIME * 1000) seconds
    private static final int CLIENTS_PER_MINUTE = 4;
    private static final int STARTING_AMOUNT = 1000000; // initial amount in bank cash desk

    private List<List<Customer>> queueList = Collections.synchronizedList(new ArrayList<>()); // List of queues for N tellers

    public void start() {
        BankCashDesk bankCashDesk = new BankCashDesk(STARTING_AMOUNT);
        System.out.println(bankCashDesk);

        for (int i = 0; i < N; i++) {
            queueList.add(new ArrayList<>());
        }

        Teller[] tellers = new Teller[N];
        Thread[] threads = new Thread[N];
        for (int i = 0; i < N; i++) {
            tellers[i] = new Teller(bankCashDesk, queueList.get(i));
            threads[i] = new Thread(tellers[i]);
            threads[i].start();
        }

        Generator generator = new Generator(queueList, SERVICE_TIME, CLIENTS_PER_MINUTE);
        Thread generation = new Thread(generator);
        generation.start();
    }

    public static void main(String[] args) {
        Bank bank = new Bank();
        bank.start();
    }
}
