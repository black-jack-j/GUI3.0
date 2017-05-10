package kcommands;

import java.sql.Connection;

public abstract class BDKCommand extends HandlerCommand {

	protected Connection connection;
	
	public BDKCommand(int priority) {
		super(priority);
	}

	@Override
	public abstract void execute();
	
	public void setConnection(Connection c){
		this.connection = c;
	}

}
