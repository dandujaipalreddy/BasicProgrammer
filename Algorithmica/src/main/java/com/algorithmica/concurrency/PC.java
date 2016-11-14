package com.algorithmica.concurrency;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The producer–consumer problem (also known as the bounded-buffer problem) is a classic example of a multi-process synchronization problem.
 * The problem describes two processes, the producer and the consumer, which share a common, fixed-size buffer used as a queue.
 */
public class PC {

    public static void main(String... args) {
        BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>();
       final Producer prod = new Producer(blockingQueue);
        final Consumer cons = new Consumer(blockingQueue);
        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++)
                    prod.produce("hello" + new Random().nextInt());
            }
        });

        Thread consumerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++)
                    System.out.println(cons.consume());
            }
        });

        ExecutorService exec = Executors.newFixedThreadPool(2);
        exec.submit(producerThread);
        exec.submit(consumerThread);
        exec.shutdown();
        System.out.println("Exiting Main");
    }

    static class Producer {
        private BlockingQueue<String> blockingQueue = null;

        public Producer(BlockingQueue bq) {
            this.blockingQueue = bq;
        }

        public void produce(String message) {
            try {
                blockingQueue.put(message);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    static class Consumer {
        private BlockingQueue<String> blockingQueue = null;

        public Consumer(BlockingQueue bq) {
            this.blockingQueue = bq;
        }

        public String consume() {
            try {
                return blockingQueue.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return null;
        }
    }


}
