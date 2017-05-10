package bserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import kcommands.KCommand;

public class Session {
	private SessionHandler handler;
	private InputStream in;
	private OutputStream out;
	
	public Session(Socket s, SessionHandler sh){
		handler = sh;
		this.register();
		this.connect(s);
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				try {
					in = s.getInputStream();
					out = s.getOutputStream();
				} catch (IOException e) {
					e.printStackTrace();
					deregister();
				}
			}
			
		});
		t.start();
	}
	
	
	
	public void register(){
		handler.query(null);
	}
	
	public void deregister(){
		handler.query(null);
	}
	
	public void update(KCommand command){
		try(ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.reset();
			oos.writeObject(oos);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			deregister();
		}
	}
	
	public void connect(Socket s){
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run() {
				try(ObjectInputStream input = new ObjectInputStream(in)) {
					while(true){
						KCommand command = (KCommand) input.readObject();
						handler.query(command);
					}
				} catch (IOException e) {
					
					e.printStackTrace();
					deregister();
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
			}
			
		});
		t.start();
	}
}
