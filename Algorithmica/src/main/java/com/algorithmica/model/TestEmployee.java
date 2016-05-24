package com.algorithmica.model;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

import com.google.gson.Gson;

public class TestEmployee implements Externalizable {

	private String empid;
	private String name;
	private String company;
	private int age;

	/**
	 * @return the empid
	 */
	public String getEmpid() {
		return empid;
	}

	/**
	 * @param empid
	 *            the empid to set
	 */
	public void setEmpid(String empid) {
		this.empid = empid;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the company
	 */
	public String getCompany() {
		return company;
	}

	/**
	 * @param company
	 *            the company to set
	 */
	public void setCompany(String company) {
		this.company = company;
	}

	/**
	 * @return the age
	 */
	public int getAge() {
		return age;
	}

	/**
	 * @param age
	 *            the age to set
	 */
	public void setAge(int age) {
		this.age = age;
	}

	public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
		// TODO Auto-generated method stub

		String json = (String) in.readObject();
		TestEmployee e = new Gson().fromJson(json, this.getClass());
		name = e.getName();
		empid = e.getEmpid();

		// System.out.println(e.toString());

	}

	public void writeExternal(ObjectOutput out) throws IOException {

		Gson g = new Gson();
		String json = g.toJson(this);
		System.out.println(json);
		out.writeObject(json);

	}

	/*
	 * (non-Javadoc)
	 *
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Employee [empid=" + empid + ", name=" + name + ", company=" + company + ", age=" + age + "]";
	}

	static boolean isAdmissibleOverpayment(double[] prices, String[] notes, double x) {

		int sum = 0;
		for (int i = 0; i < notes.length; i++) {
			if (notes[i].contains("%")) {
				double rate = Double.parseDouble(notes[i].split("%", 0)[0]);
				double percentage = rate / 100;
				double inc = 0;
				if (notes[i].contains("higher")) {
					inc = 1 + 1 / percentage;
					System.out.println(prices[i] / inc);

				} else if (notes[i].contains("lower")) {
					inc = 1 - 1 / percentage;
					System.out.println(prices[i] / inc);

				}
				sum += prices[i] / inc;
			}
		}
		System.out.println(sum);
		return x >= sum;
	}

	public static void main(String[] args) {

		double prices[] = { 48, 165 };
		String notes[] = { "20.00% lower than in-store", "10.00% higher than in-store" };
		double x = 2;

		System.out.println(isAdmissibleOverpayment(prices, notes, x));
	}

}
