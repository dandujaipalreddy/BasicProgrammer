package com.algorithmica.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.algorithmica.model.Employee;
import com.algorithmica.model.TestEmployee;

public class CustomSerialization {

	private static ObjectInputStream oin;
	private static ObjectOutputStream oout;

	public static void serializeEmployee(String fileName) {

		try {
			FileOutputStream fout = new FileOutputStream(new File(fileName));
			oout = new ObjectOutputStream(fout);

			Employee p = new Employee();
			p.setAge(22);
			p.setCompany("India");
			p.setEmpid("ME100");
			p.setName("testObject");

			oout.writeObject(p);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deserializeEmployee(String fileName) {

		try {
			FileInputStream fin = new FileInputStream(new File(fileName));
			oin = new ObjectInputStream(fin);
			Employee p = (Employee) oin.readObject();
			System.out.println("Person TO String : " + p.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void serializeTestEmployee(String fileName) {

		try {
			FileOutputStream fout = new FileOutputStream(new File(fileName));
			oout = new ObjectOutputStream(fout);

			TestEmployee p = new TestEmployee();
			p.setAge(22);
			p.setCompany("India");
			p.setEmpid("ME100");
			p.setName("testObject_0000");

			oout.writeObject(p);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void deserializeTestEmployee(String fileName) {

		try {
			FileInputStream fin = new FileInputStream(new File(fileName));
			oin = new ObjectInputStream(fin);
			TestEmployee p = (TestEmployee) oin.readObject();
			System.out.println("Person TO String : " + p.toString());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {

		serializeTestEmployee("Employee_test.json");
		deserializeTestEmployee("Employee_test.json");

	}

}
