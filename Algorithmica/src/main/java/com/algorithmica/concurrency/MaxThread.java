package com.algorithmica.concurrency;

import java.util.Random;

public class MaxThread extends Thread {

	private int lo, hi;
	private int[] arr;
	private int max = Integer.MIN_VALUE;

	public void run() {
		for (int i = lo; i < hi; i++)
			if (arr[i] > max) max = arr[i];

	}

	public MaxThread(int[] arr, int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
		this.arr = arr;
	}

	public static int findMax(int[] arr) throws InterruptedException {
		int max = Integer.MIN_VALUE;
		MaxThread[] maxThread = new MaxThread[4];
		int len = arr.length;
		for (int i = 0; i < 4; i++) {
			maxThread[i] = new MaxThread(arr, (i * len) / 4, ((i + 1) * len / 4));
			maxThread[i].start();
		}
		for (int i = 0; i < 4; i++) {
			maxThread[i].join();
			if(max<maxThread[i].max) max=maxThread[i].max;
		}
		return max;
	}

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		int[] arr=new int[100000];
		Random r=new Random(100);
		for(int i=0;i<100000;i++)
			arr[i]=r.nextInt(100000);
		long start=System.nanoTime();
		System.out.println(findMax(arr));
		long end=System.nanoTime();
		System.out.println("Elapsed Time:"+(end-start));

	}

}
