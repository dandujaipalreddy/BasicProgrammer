package com.algorithmica.basicthinking;

public class AssignmentOne {

	/*
	 * Given an array that contains some number of contiguous zeroes at the
	 * start, followed by some arbitrary integers other than zero. Write an
	 * efficient function that returns the number of zeroes in the given array
	 *
	 */
	public static int countZeros(int[] a, int n) {

		int count = 0;
		while (count < n && a[count] == 0)
			count++;
		return count;
	}

	/*
	 * Given a real number x, and a sequence of real numbers c0, c1, …cn, Write
	 * an efficient function to find out the value of following polynomial of
	 * degree ‘n’: pn(x) = cnx^n + c n-1xn-1 + … + c2x2 + c1x + c0
	 *
	 *
	 * https://www.codechef.com/problems/PD23
	 */

	public static double EvalPolynom(int[] coef, int x, int n) {

		double result = coef[0];
		long interX = x;
		for (int i = 1; i <= n; i++) {

			result += (coef[i] * interX);
			interX *= x;
		}
		return result;
	}

	/*
	 * Given an array of N integers in which each element is between 1 and N-1,
	 * write an efficient function to determine the any duplicated integer. You
	 * may destroy the array. What are the time and space complexities of your
	 * solution?
	 */

	public static int FindDuplicateWithExtraSpace(int[] a) {
		boolean[] duplicates = new boolean[a.length];
		for (int i = 0; i < a.length; i++) {
			if (duplicates[a[i]])
				return a[i];
			duplicates[a[i]] = true;
		}

		return -1;
	}

	public static int FindDuplicateWithModifyingTheArray(int[] a) {
		int n = a.length;

		for (int i = 0; i < n; i++) {

			int index = Math.abs(a[i]) - 1;
			if (a[index] > 0)
				a[index] = -a[index];
			else
				return Math.abs(a[index]);
		}
		return -1;
	}

	/*
	 * Given an array of size M+N in which first M numbers are sorted in
	 * non-decreasing order and last N slots are empty. Also given an another
	 * array of size N which is sorted in non-decreasing order. Write an
	 * efficient function to merge these two arrays without using any extra
	 * space so that the array of M+N size is sorted. Function Prototype: void
	 * Merge(int[ ] a1, int[ ] a2, int m, int n) // array a1 is of size m+n
	 */

	public static void Merge(int[] a1, int[] a2, int m, int n) {

	}

}
