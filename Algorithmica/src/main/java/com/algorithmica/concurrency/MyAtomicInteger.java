package com.algorithmica.concurrency;

public class MyAtomicInteger {
    private int amount;

    public synchronized int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

}
