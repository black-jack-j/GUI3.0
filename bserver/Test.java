package bserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import kcommands.ConcreteCommand;
import kcommands.KCommand;

public class Test {
	public static void main(String[] args){
		try {
			ByteArrayOutputStream bytearr = new ByteArrayOutputStream();
			ObjectOutputStream oos = new ObjectOutputStream(bytearr);
			KCommand command = new ConcreteCommand(1);
			oos.writeObject(command);
			String s = bytearr.toString();
			byte[] array = s.getBytes();
			ByteArrayInputStream basi = new ByteArrayInputStream(array);
			ObjectInputStream ois = new ObjectInputStream(basi);
			KCommand c2 = (KCommand) ois.readObject();
			c2.execute();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
