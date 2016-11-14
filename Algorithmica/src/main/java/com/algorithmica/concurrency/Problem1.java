package com.algorithmica.concurrency;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * There are three threads in a process.
 * The first thread prints 1 1 1 …, the second one prints 2 2 2 …, and the third one prints 3 3 3 … endlessly.
 * How do you schedule these three threads in order to print 1 2 3 1 2 3 …?
 */
public class Problem1 {

    public static void main(String[] args) {
        final Lock lock = new ReentrantLock();
        final Condition condition = lock.newCondition();

       ThreadId threadId = new Problem1.ThreadId();
        threadId.setId(1);
        Thread t1 = setThread(lock, condition, 1, 2, threadId);
        Thread t2 = setThread(lock, condition, 2, 3, threadId);
        Thread t3 = setThread(lock, condition, 3, 1, threadId);
        t1.start();
        t2.start();
        t3.start();

    }

    private static Thread setThread(final Lock lock, final Condition condition,final int actualThreadId, final int nextThreadId, final ThreadId threadId) {
        Thread thread = new Thread() {
            @Override
            public void run() {
                while (true) {
                    lock.lock();
                    try {
                        while (threadId.getId() != actualThreadId)
                            try {
                                condition.await();
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        System.out.println("" + actualThreadId);
                        threadId.setId(nextThreadId);
                        condition.signalAll();
                    } finally {
                        lock.unlock();
                    }
                }
            }
        };
        return thread;
    }

    private static class ThreadId {
        private int id;

        public ThreadId() {
        }

        public final int getId() {
            return id;
        }

        public  final void setId(int id) {
            this.id = id;
        }
    }
}