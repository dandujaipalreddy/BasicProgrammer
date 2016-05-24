package com.algorithmica.performance;

import java.util.ArrayList;
import java.util.List;

public class UsedMemory {
	private static final long MEGABYTE = 1024L * 1024L;

	public static long bytesToMegabytes(long bytes) {
		return bytes / MEGABYTE;
	}

	public static void main(String[] args) {
		// I assume you will know how to create a object Person yourself...
		List<Integer> list = new ArrayList<Integer>();
		for (int i = 0; i <= 100000; i++) {
			list.add(i);
		}
		System.out.println(UsedMemory.class.getClassLoader().toString());
		// Get the Java runtime
		Runtime runtime = Runtime.getRuntime();
		// Run the garbage collector
		runtime.gc();
		// Calculate the used memory
		long memory = runtime.totalMemory() - runtime.freeMemory();
		System.out.println("Used memory is bytes: " + memory);
		System.out.println("Used memory is megabytes: " + bytesToMegabytes(memory));
	}
}
