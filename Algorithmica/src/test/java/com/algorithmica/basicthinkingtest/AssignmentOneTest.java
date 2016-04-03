package com.algorithmica.basicthinkingtest;

import org.junit.Assert;
import org.junit.Test;

import com.algorithmica.basicthinking.AssignmentOne;

public class AssignmentOneTest {

	@Test
	public void CountZerosTest() {

		int a[] = { 0, 0, 0, 0, 0, 3, 2, 8, 11, 10, 15, 22 };
		Assert.assertEquals(5, AssignmentOne.countZeros(a, a.length));

		int b[] = { 3, 2, 8, 11, 10, 15, 22 };
		Assert.assertEquals(0, AssignmentOne.countZeros(b, b.length));

		int c[] = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		Assert.assertEquals(c.length, AssignmentOne.countZeros(c, c.length));
	}

	@SuppressWarnings("deprecation")
	@Test
	public void EvalPolynomTest() {

		Assert.assertEquals(-1.0, AssignmentOne.EvalPolynom(new int[] { -1, 2, 1 }, 0, 2), 0);
		Assert.assertEquals(-9.0, AssignmentOne.EvalPolynom(new int[] { -1, -2, 1, 2 }, -2, 3), 0);
		Assert.assertEquals(15.0, AssignmentOne.EvalPolynom(new int[] { -1, -2, 1, 2 }, 2, 3), 0);

	}

	@Test
	public void FindDuplicateWithExtraSpaceTest() {

		Assert.assertEquals(5, AssignmentOne.FindDuplicateWithExtraSpace(new int[] { 2, 1, 3, 4, 5, 5 }));
		Assert.assertEquals(1, AssignmentOne.FindDuplicateWithExtraSpace(new int[] { 1, 2, 3, 4, 5, 1 }));
		Assert.assertEquals(1, AssignmentOne.FindDuplicateWithExtraSpace(new int[] { 1, 1, 1, 1, 1, 1 }));
		Assert.assertEquals(1, AssignmentOne.FindDuplicateWithExtraSpace(new int[] { 1, 2, 1, 1, 2, 1 }));
	}

	@Test
	public void FindDuplicateWithModifyingTheArrayTest() {

		Assert.assertEquals(5, AssignmentOne.FindDuplicateWithModifyingTheArray(new int[] { 2, 1, 3, 4, 5, 5 }));
		Assert.assertEquals(1, AssignmentOne.FindDuplicateWithModifyingTheArray(new int[] { 1, 2, 3, 4, 5, 1 }));
		Assert.assertEquals(1, AssignmentOne.FindDuplicateWithModifyingTheArray(new int[] { 1, 1, 1, 1, 1, 1 }));
		Assert.assertEquals(1, AssignmentOne.FindDuplicateWithModifyingTheArray(new int[] { 1, 2, 1, 1, 2, 1 }));
	}
}
