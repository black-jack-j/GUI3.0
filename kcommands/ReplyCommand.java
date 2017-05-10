package kcommands;

public class ReplyCommand extends KCommand {

	public ReplyCommand(int priority) {
		super(priority);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		System.out.println("I've got the reply");
	}

}
