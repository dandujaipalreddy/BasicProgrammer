package com.algorithmica.concurrency;

public class ReverseHello extends Thread {

	private int count=0;
	public ReverseHello(int count){
		this.count=count;
	}
	public void run(){
		if(count!=50){
			ReverseHello a=new ReverseHello(count+1);
			a.start();
			try {
				a.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("Hello from thread :"+count );
		}
		else{

			System.out.println("Hello from thread :"+count );
		}

	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		ReverseHello a=new ReverseHello(1);
		a.start();

	}

}
