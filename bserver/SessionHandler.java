package bserver;

import java.util.List;
import java.util.concurrent.PriorityBlockingQueue;

import kcommands.KCommand;

public class SessionHandler {
	private int id;
	private PriorityBlockingQueue<KCommand> pipe;
	private List<Session> listeners;
	
	public SessionHandler(int id){
		this.id = id;
	}
	
	public void addListener(Session client){
		listeners.add(client);
	}
	
	public void removeListener(Session client){
		listeners.remove(client);
	}
	
	public void notifyListeners(KCommand command){
		listeners.forEach((Session s)->{
			s.update(command);
		});
	}
	public void notifyConcrete(Session s, KCommand command){
		if (listeners.indexOf(s)!=-1) s.update(command);
	}
	
	public void query(KCommand command){
		this.pipe.add(command);
	}
	public int getId(){
		return id;
	}
	
	@Override
	public boolean equals(Object sessionhandler){
		return ((SessionHandler)sessionhandler).getId() == this.getId();
	}
}
