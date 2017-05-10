package bserver;

import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
	private CopyOnWriteArrayList<SessionHandler> handlers;
	private final int port;
	
	public Server(int p){
		port = p;
		handlers = new CopyOnWriteArrayList<>();
	}
	
	public SessionHandler createSession(int id){
		SessionHandler newS = new SessionHandler(id);
		int index;
		if((index = handlers.indexOf(newS))!=-1) return handlers.get(index);
		handlers.add(newS);
		return newS;
	}
	
}
