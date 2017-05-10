package bserver;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import kcommands.HandlerCommand;
import kcommands.KCommand;

public class SessionHandler {
	private int id;
	private Server server;
	private PriorityBlockingQueue<HandlerCommand> pipe;
	private CopyOnWriteArrayList<Session> listeners;
	
	public SessionHandler(int id){
		this.id = id;
		pipe = new PriorityBlockingQueue<HandlerCommand>();
		listeners = new CopyOnWriteArrayList<>();
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				work();
			}
			
		});
		t.start();
	}
	
	public void addListener(Session client){
		listeners.add(client);
	}
	
	public void removeListener(Session client){
		listeners.remove(client);
	}
	
	public void notifyListeners(KCommand command){
		listeners.forEach((Session s)->{
			Thread t = new Thread(new Runnable(){

				@Override
				public void run() {
					s.update(command);
				}
				
			});
			t.start();
		});
	}
	public void notifyConcrete(Session s, KCommand command){
		if (listeners.indexOf(s)!=-1) s.update(command);
	}
	
	public void query(HandlerCommand command){
		this.pipe.add(command);
	}
	public int getId(){
		return id;
	}
	
	@Override
	public boolean equals(Object sessionhandler){
		return ((SessionHandler)sessionhandler).getId() == this.getId();
	}
	
	public void work(){
		while(true){
			//следит за добавлением команд в очередь
		}
	}
}
