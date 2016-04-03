package com.algorithmica.serialization;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import com.algorithmica.model.Person;

/*This class demonstrates the basic serialization Mechanism in Java*/
/*When an object is serialized, objectOutputStream.writeObject (to save this object) is invoked and
 * when an object is read, ObjectInputStream.readObject () is invoked.*/
public class BasicSerialization {

	public static void serializePerson(String fileName) {

		try {
			FileOutputStream fout = new FileOutputStream(new File(fileName));
			ObjectOutputStream oout = new ObjectOutputStream(fout);

			Person p = new Person();
			p.setAge(22);
			p.setCountry("India");
			p.setUid(1000);
			p.setName("testObject");

			oout.writeObject(p);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static Person deserialize(String fileName) {

		Person p = null;
		try {
			FileInputStream fin = new FileInputStream(new File(fileName));
			ObjectInputStream oin = new ObjectInputStream(fin);
			p = (Person) oin.readObject();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return p;
	}

	public static void main(String[] args) {

		serializePerson("person.ser");
		Person p = deserialize("person.ser");
		System.out.println(p.toString());
	}

}
