package kcommands;

public abstract class ClientCommand extends KCommand {

	public ClientCommand(int priority) {
		super(priority);
		// TODO Auto-generated constructor stub
	}

	@Override
	public abstract void execute();

}
