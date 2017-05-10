package bserver;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import kcommands.DeregisterCommand;
import kcommands.HandlerCommand;
import kcommands.KCommand;

public class Session {
	private SessionHandler handler;
	private BlockingQueue<KCommand> pipe;
	private InputStream in;
	private OutputStream out;
	private Socket socket;
	
	public Session(Socket s, SessionHandler sh){
		handler = sh;
		handler.addListener(this);
		socket = s;
		pipe = new LinkedBlockingQueue<KCommand>();
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
	
	public void update(KCommand command){
		/**try(ObjectOutputStream oos = new ObjectOutputStream(out)) {
			oos.reset();
			oos.writeObject(oos);
			oos.flush();
		} catch (IOException e) {
			e.printStackTrace();
			deregister();
		}**/
		pipe.add(command);
	}
	
	public void send(){
		try {
			while(true){
				KCommand reply = pipe.take();
				ByteArrayOutputStream bytearr = new ByteArrayOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(bytearr);
				oos.writeObject(reply);
				String s = bytearr.toString();
				DataOutputStream dos = new DataOutputStream(out);
				dos.writeUTF(s);
				dos.flush();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void connect(){
		Thread t = new Thread(new Runnable(){
			
			@Override
			public void run() {
				DataInputStream input = new DataInputStream(in);
				String s = null;
				try{
					while(true){
						s = input.readUTF();
						byte[] array = s.getBytes();
						ByteArrayInputStream basi = new ByteArrayInputStream(array);
						ObjectInputStream ois = new ObjectInputStream(basi);
						HandlerCommand command = (HandlerCommand) ois.readObject();
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
