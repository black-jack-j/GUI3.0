package bserver;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

import kcommands.ConcreteCommand;
import kcommands.KCommand;

public class Session {
	private SessionHandler handler;
	private InputStream in;
	private OutputStream out;
	private Socket socket;
	
	public Session(Socket s, SessionHandler sh){
		handler = sh;
		handler.addListener(this);
		socket = s;
	}

	public void initConnection() {
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			//deregister();
		}
		System.out.println("OK");
	}
	
	public Session(SessionHandler sh){
		handler = sh;
		handler.addListener(this);
	}
	
	public void register(){
		handler.query(null);
	}
	
	public void deregister(){
		handler.query(null);
	}
	
	public void update(KCommand command){
		/**try(ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.reset();
			oos.writeObject(oos);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			deregister();
		}**/
		command.execute();
	}
	
	public void connect(){
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run() {
				try {
					in = socket.getInputStream();
					DataInputStream input = new DataInputStream(in);
					String s = null;
					while(true){
						s = input.readUTF();
						byte[] array = s.getBytes();
						ByteArrayInputStream basi = new ByteArrayInputStream(array);
						ObjectInputStream ois = new ObjectInputStream(basi);
						KCommand command = (KCommand) ois.readObject();
						handler.query(command);
					}
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		});
		t.start();
	}
	public void connect(KCommand command){
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				handler.query(command);
			}
			
		});
		t.start();
	}
	public static void main(String[] args){
		SessionHandler sh = new SessionHandler(1);
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run(){
				sh.work();
			}
			
		});
		t.start();
		Session one = new Session(sh);
		for(int i = 0;i<10;i++){
			KCommand a = new ConcreteCommand(i);
			one.connect(a);
		}
	}
}
