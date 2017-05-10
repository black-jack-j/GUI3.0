package bserver;

import java.net.Socket;

public class Handler implements Runnable {
	
	private Socket socket;
	private SessionHandler s;
	public Handler(SessionHandler sh, Socket s){
		socket = s;
		this.s = sh;
	}
	
	
	@Override
	public void run() {
		Session session = new Session(socket, s);
		session.connect();
	}

}
