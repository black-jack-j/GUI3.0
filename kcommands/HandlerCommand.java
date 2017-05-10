package kcommands;

import bserver.SessionHandler;

public abstract class HandlerCommand extends KCommand {

	public HandlerCommand(int priority) {
		super(priority);
	}

	protected SessionHandler handler;
	
	public void setHandler(SessionHandler sh){
		this.handler = sh;
	}

}
