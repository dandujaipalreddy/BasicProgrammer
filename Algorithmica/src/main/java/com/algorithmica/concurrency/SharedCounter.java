package com.algorithmica.concurrency;

public class SharedCounter {

	private int sharedCounter = 0;

	public void increment() {
		sharedCounter++;
	}

	public int getSharedCounter() {

		return sharedCounter;

	}

	public static void main(String[] args) throws InterruptedException {

		final SharedCounter a = new SharedCounter();
		Runnable r=new Runnable() {

			@Override
			public void run() {
				// TODO Auto-generated method stub
				for(int i=0;i<100;i++)
					a.increment();

			}
		};

		for(int i=0;i<10;i++){
			Thread t=new Thread(r);
			t.start();

		}

		Thread.sleep(10000);
		System.out.println(a.getSharedCounter());
	}
}
