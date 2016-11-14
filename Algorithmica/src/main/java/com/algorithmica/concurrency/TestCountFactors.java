package com.algorithmica.concurrency;

class TestCountFactors {
	public static void main(String[] args) {
		final int range = 5_000_000;
		int count = 0;
		Thread[] t;
		long start = System.currentTimeMillis();
		for (int p = 0; p < range; p++)
			count += countFactors(p);
		long end = System.currentTimeMillis();
		System.out.println("Time Taken : " + (end - start));
		System.out.printf("Total number of factors is %9d%n", count);
	}

	public static int countFactors(int p) {
		if (p < 2)
			return 0;
		int factorCount = 1, k = 2;
		while (p >= k * k) {
			if (p % k == 0) {
				factorCount++;
				p /= k;
			} else
				k++;
		}
		return factorCount;
	}
}
