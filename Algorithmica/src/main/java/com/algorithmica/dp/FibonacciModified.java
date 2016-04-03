package com.algorithmica.dp;

import java.math.BigInteger;
import java.util.Scanner;

import sun.security.util.BigInt;

public class FibonacciModified {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		int first = in.nextInt();
		int second = in.nextInt();
		int n = in.nextInt();
		System.out.println(fibModified(first, second, n));

	}

	public static String fibModified(int first, int second, int n) {

		BigInteger[] dp = new BigInteger[n];
		dp[0] = new BigInteger(Integer.toString(first));
		dp[1] = new BigInteger(Integer.toString(second));
		for (int i = 2; i < n; i++) {

			BigInteger power = dp[i - 1].pow(2);
			dp[i] = power.add(dp[i - 2]);

		}
		return dp[n - 1].toString();

	}

	public static int candies(int array[], int n) {
		int candy_total = 1;
		int present_number = 1;
		for (int i = 1; i < n; i++) {

			if (array[i] > array[i - 1]) {
				present_number++;
				candy_total += present_number;
			} else{
				candy_total += 1;
				present_number = 1;
			}
		}
		return candy_total;

	}

}
