package bserver;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

import kcommands.ConcreteCommand;
import kcommands.KCommand;

public class TestClient {
	public static void main(String[] args){
		try {
			Socket s = new Socket("localhost", 1488);
			InputStream in = s.getInputStream();
			OutputStream out = s.getOutputStream();
			Thread t1 = new Thread(new Runnable(){

				@Override
				public void run() {
					try {
						ByteArrayOutputStream bytearr = new ByteArrayOutputStream();
						ObjectOutputStream oos = new ObjectOutputStream(bytearr);
						KCommand request = new ConcreteCommand(1);
						oos.writeObject(request);
						String s = bytearr.toString();
						DataOutputStream dos = new DataOutputStream(out);
						dos.writeUTF(s);
						dos.flush();
						
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				
			});
		t1.start();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
