package bserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
	private CopyOnWriteArrayList<SessionHandler> handlers;
	private final int port;
	private ServerSocket server;
	
	public Server(int p){
		port = p;
		handlers = new CopyOnWriteArrayList<>();
		try {
			server = new ServerSocket(p);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public synchronized SessionHandler createSession(int id){
		SessionHandler newS = new SessionHandler(id);
		int index;
		if((index = handlers.indexOf(newS))!=-1) return handlers.get(index);
		handlers.add(newS);
		return newS;
	}
	public void printStorage(){
		handlers.forEach((SessionHandler s)->System.out.println(s.getId()));
	}
	public void start(){
		while(true){
			try {
				Socket socket = server.accept();
				SessionHandler s = createSession(1);
				Session session = new Session(socket, s);
				session.initConnection();
				Thread t = new Thread(new Runnable(){

					@Override
					public void run() {
						session.connect();
					}
					
				});
				t.start();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		Server s = new Server(1488);
		s.start();
	}
}
