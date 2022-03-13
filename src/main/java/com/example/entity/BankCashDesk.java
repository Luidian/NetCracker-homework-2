package com.example.entity;

/**
 * @author Alexanrd Spaskin
 */
public class BankCashDesk {

    private int cash;

    public BankCashDesk(int cash) {
        this.cash = cash;
    }

    public int getCash() {
        return cash;
    }

    public synchronized void cashWithdrawal(int amount){
        if (cash > amount) {
            cash -= amount;
        }else {
            //TODO: do something
        }
    }

    public synchronized void cashReplenishment(int amount) {
        cash += amount;
    }

    @Override
    public String toString() {
        return "BankCashDesk{" +
                "cash=" + cash +
                '}';
    }
}
