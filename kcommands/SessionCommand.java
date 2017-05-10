package kcommands;

public abstract class SessionCommand extends HandlerCommand {

	public SessionCommand(int priority) {
		super(priority);
	}

	@Override
	public abstract void execute();

}
