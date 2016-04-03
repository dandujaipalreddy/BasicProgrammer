package com.algorithmica.concurrency;

class Thread1 extends Thread {

	public void run() {
		for (int i = 0; i < 10000; i++)
			System.out.println("ThreadOne");

	}

}

class Thread2 extends Thread {

	public void run() {

		for (int i = 0; i < 10000; i++)
			System.out.println("ThreadTwo");
	}

}

public class BasicThreadDemo {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		Thread1 one = new Thread1();
		Thread2 two = new Thread2();
		one.start();
		two.start();
		// Join call on a called thread makes calling thread wait until the
		// called finish it's execution
		one.join();
		two.join();
		System.out.println("Thread Exiting Main ******************");

	}

}
