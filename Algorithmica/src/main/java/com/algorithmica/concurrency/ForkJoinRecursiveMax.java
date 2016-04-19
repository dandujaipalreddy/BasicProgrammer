package com.algorithmica.concurrency;

import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinRecursiveMax extends RecursiveTask<Integer> {

	/**
	 *
	 */
	private static final long serialVersionUID = 1L;

	public static final int SEQUENTIAL_THRESHOLD = 10;

	private int lo, hi;
	private int[] arr;

	public ForkJoinRecursiveMax(int[] arr, int lo, int hi) {
		this.lo = lo;
		this.hi = hi;
		this.arr = arr;

	}

	@Override
	protected Integer compute() {
		if (hi - lo <= SEQUENTIAL_THRESHOLD) {
			int max = 0;
			for (int i = lo; i < hi; i++) {
				if (max < arr[i]) max = arr[i];
			}
			return max;
		} else {

			int mid = (lo + hi) / 2;
			ForkJoinRecursiveMax left = new ForkJoinRecursiveMax(arr, lo, mid);
			ForkJoinRecursiveMax right = new ForkJoinRecursiveMax(arr, mid, hi);
			left.fork();
			int rightAns = right.compute();
			int leftAns = left.join();
			return leftAns > rightAns ? leftAns : rightAns;
		}

	}

	private static final ForkJoinPool fjPool = new ForkJoinPool();

	public static int findMax(int[] arr) throws InterruptedException {
		return fjPool.invoke(new ForkJoinRecursiveMax(arr, 0, arr.length));

	}

	public static void main(String[] args) throws InterruptedException {

		int[] arr = new int[100000];
		Random r = new Random(100);
		for (int i = 0; i < 100000; i++)
			arr[i] = r.nextInt(100000);
		long start = System.nanoTime();
		System.out.println(findMax(arr));
		long end = System.nanoTime();
		System.out.println("Elapsed Time:" + (end - start));

	}

}
