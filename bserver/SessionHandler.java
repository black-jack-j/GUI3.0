package bserver;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.PriorityBlockingQueue;

import kcommands.BDKCommand;
import kcommands.ClientCommand;
import kcommands.HandlerCommand;
import kcommands.TestCommand;

public class SessionHandler {
	private int id;
	private Server server;
	private PriorityBlockingQueue<HandlerCommand> pipe;
	private CopyOnWriteArrayList<Session> listeners;
	private Connection connection;
	
	public SessionHandler(int id){
		this.id = id;
		pipe = new PriorityBlockingQueue<HandlerCommand>();
		listeners = new CopyOnWriteArrayList<>();
		try {
			Class.forName("org.postgresql.Driver");
			connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/newDB", "postgres", "evening64night");
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				work();
			}
			
		});
		t.start();
		System.out.println("constructor");
	}
	
	public synchronized void addListener(Session client){
		listeners.add(client);
	}
	
	public synchronized void removeListener(Session client){
		listeners.remove(client);
	}
	
	public void notifyListeners(ClientCommand command){
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
	
	public void query(HandlerCommand command){
		if (command instanceof BDKCommand) {
			command.setHandler(this);
			((BDKCommand) command).setConnection(connection);
		}
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
				System.out.println("before getting");
				HandlerCommand hc = this.pipe.take();
				System.out.println("after getting");
				hc.execute();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		SessionHandler sh = new SessionHandler(1);
		sh.query(new TestCommand(0,0));
	}
}
