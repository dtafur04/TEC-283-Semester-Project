package com.tafur;

public class Player {
    private String name;
    private double balance;
    private double totalDeposited;

    public Player(String name) {
        this.name = name;
        this.balance = 0;
        this.totalDeposited = 0;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public double getTotalDeposited() {
        return totalDeposited;
    }

    public void deposit(double amount) {
        this.balance += amount;
        this.totalDeposited += amount;
    }

    public void updateBalance(double amount) {
        this.balance += amount;
    }
}
