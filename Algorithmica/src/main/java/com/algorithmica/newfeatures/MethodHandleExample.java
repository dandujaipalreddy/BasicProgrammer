package com.algorithmica.newfeatures;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

/*The main benefit of MethodHandles is that you can create a reference to a method which type is only known at runtime
 *  and efficiently look it up and invoke it.*/
public class MethodHandleExample {

	public String sayHello(String name) {
		String message = "Hello " + name;
		System.out.println(message);
		return message;
	}

	public static void main(String[] args) throws Throwable {
		// TODO Auto-generated method stub

		MethodHandle sayHelloHandle = MethodHandles.lookup().findVirtual(MethodHandleExample.class, "sayHello",
				MethodType.methodType(String.class, String.class));

		MethodHandle ready = sayHelloHandle.bindTo(new MethodHandleExample());
		ready.invokeWithArguments("jaipal");
		ready.invokeWithArguments("cse");

	}

}
