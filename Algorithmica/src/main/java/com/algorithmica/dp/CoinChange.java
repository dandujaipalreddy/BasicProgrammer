package com.algorithmica.dp;

import java.util.Scanner;

/*How many different ways can you make change for an amount, given a list of coins
 * idea:Solution Containing coin i + solutions which doesnot contain coin i
 * */
public class CoinChange {
	public static void main(String[] args) {

		Scanner in=new Scanner(System.in);
		int amount=in.nextInt();
		int n=in.nextInt();
		int[] a=new int[n];
		for(int i=0;i<n;i++)
			a[i]=in.nextInt();
		System.out.println(CountCoinsDP(a, amount));

	}

	public static int CountCoinsRecursive(int[] coins, int coin, int sum) {

		if (sum == 0)
			return 1;
		if (sum < 0)
			return 0;
		if (coin <= 0 && sum >= 1)
			return 0;
		return CountCoinsRecursive(coins, coin - 1, sum) + CountCoinsRecursive(coins, coin, sum - coins[coin - 1]);
	}

	public static long CountCoinsDP(int[] v, int amount) {
		long[][] solution = new long[v.length + 1][amount + 1];

		// if amount=0 then just return empty set to make the change
		for (int i = 0; i <= v.length; i++) {
			solution[i][0] = 1;
		}

		// if no coins given, 0 ways to change the amount
		for (int i = 1; i <= amount; i++) {
			solution[0][i] = 0;
		}

		// now fill rest of the matrix.

		for (int i = 1; i <= v.length; i++) {
			for (int j = 1; j <= amount; j++) {
				// check if the coin value is less than the amount needed
				if (v[i - 1] <= j) {
					// reduce the amount by coin value and
					// use the subproblem solution (amount-v[i]) and
					// add the solution from the top to it
					solution[i][j] = solution[i - 1][j] + solution[i][j - v[i - 1]];
				} else {
					// just copy the value from the top
					solution[i][j] = solution[i - 1][j];
				}
			}
		}
		return solution[v.length][amount];
	}

}
