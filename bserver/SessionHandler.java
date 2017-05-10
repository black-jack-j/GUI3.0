package bserver;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import kcommands.KCommand;
import kcommands.ReplyCommand;

public class SessionHandler {
	private int id;
	private Server server;
	private PriorityBlockingQueue<KCommand> pipe;
	private CopyOnWriteArrayList<Session> listeners;
	
	public SessionHandler(int id){
		this.id = id;
		pipe = new PriorityBlockingQueue<KCommand>();
		listeners = new CopyOnWriteArrayList<>();
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
	
	public void work(){
		while(true){
			try {
				KCommand command = pipe.take();
				command.execute();
				KCommand reply = new ReplyCommand(1);
				notifyListeners(reply);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
