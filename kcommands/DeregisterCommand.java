package kcommands;

import bserver.Session;

public class DeregisterCommand extends SessionCommand {

	private Session session;
	
	public DeregisterCommand(Session session, int priority) {
		super(priority);
		this.session = session;
	}

	@Override
	public void execute() {
		handler.removeListener(session);
	}

}
