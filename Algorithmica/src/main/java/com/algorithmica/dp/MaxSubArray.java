package com.algorithmica.dp;

import java.util.Scanner;

public class MaxSubArray {

	/*
	 * Given an array of Elements.Find Maximum possible sum of contiguous arrray
	 * and Non-contiguous array Ex: 2 -1 2 3 4 -5 o/p:10 11
	 */

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		int t = in.nextInt();
		while (t > 0) {
			int n = in.nextInt();
			int[] a = new int[n];
			for (int i = 0; i < n; i++)
				a[i] = in.nextInt();

			int csum = MaxContiguousSum(a);
			int sum = MaxNonContiguousSum(a);
			System.out.println(csum + " " + sum);
			t--;
		}

	}

	public static int MaxContiguousSum(int[] array) {

		int maxSum = array[0];
		int currSum = array[0];
		for (int i = 1; i < array.length; i++) {
			currSum = Math.max(currSum + array[i], array[i]);
			maxSum = Math.max(currSum, maxSum);
		}

		return maxSum;
	}

	public static int MaxNonContiguousSum(int[] array) {
		int sum = array[0];
		for (int i = 1; i < array.length; i++)
			sum = Math.max(Math.max(sum,sum + array[i]),array[i]);

		return sum;
	}
}
