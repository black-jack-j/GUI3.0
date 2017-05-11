package bserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kcommands.ClientCommand;
import kcommands.DeregisterCommand;
import kcommands.HandlerCommand;

public class Session {
	private SessionHandler handler;
	private BlockingQueue<ClientCommand> pipe;
	private InputStream in;
	private OutputStream out;
	private Socket socket;
	
	public Session(Socket s, SessionHandler sh){
		handler = sh;
		handler.addListener(this);
		socket = s;
		pipe = new LinkedBlockingQueue<ClientCommand>();
	}

	public void initConnection() {
		try {
			in = socket.getInputStream();
			out = socket.getOutputStream();
		} catch (IOException e) {
			e.printStackTrace();
			//deregister();
		}
	}
	
	public Session(SessionHandler sh){
		handler = sh;
		handler.addListener(this);
	}
	
	public void register(){
		handler.query(null);
	}
	
	public void deregister(){
		handler.query(new DeregisterCommand(this, 10));  //client disconnected - no more updates for his terminal
	}
	
	public void update(ClientCommand command){
		pipe.add(command);
	}
	
	public void send(){
		try {
			while(true){
				ClientCommand reply = pipe.take();
				ObjectOutputStream oos = new ObjectOutputStream(out);
				oos.writeObject(reply);
				oos.flush();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			deregister();
		}
	}
	
	public void connect(){
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run() {
				try{
					ObjectInputStream input = new ObjectInputStream(in);
					while(true){
						HandlerCommand command = (HandlerCommand) input .readObject();
						handler.query(command);
					}
				} catch (IOException e) {
					e.printStackTrace();
					//deregister();
				} catch (ClassNotFoundException e) {
					
					e.printStackTrace();
				}
			}
			
		});
		t.start();
	}
	public static void main(String[] args){
		
	}
}
