package com.algorithmica.concurrency;

import java.util.LinkedList;
import java.util.List;

public class MyBlockingQueue {

	private List<Object> queue = new LinkedList<Object>();
	private int limit = 10;

	public MyBlockingQueue(int limit) {
		this.limit = limit;
	}

	public synchronized void enqueue(Object item) throws InterruptedException {
		while (this.queue.size() == this.limit) {
			wait();
		}
		if (this.queue.size() == 0) {
			notifyAll();
		}
		this.queue.add(item);
	}

	public synchronized Object dequeue() throws InterruptedException {
		while (this.queue.size() == 0) {
			wait();
		}
		if (this.queue.size() == this.limit) {
			notifyAll();
		}

		return this.queue.remove(0);
	}

	public static void main(String[] args) throws InterruptedException {

		MyBlockingQueue blockingQueue=new MyBlockingQueue(10);
		for(int i=0;i<10;i++){

			blockingQueue.enqueue(new Integer(10));
			System.out.println(blockingQueue.dequeue());
			System.out.println(blockingQueue.dequeue());

		}
	}

}
