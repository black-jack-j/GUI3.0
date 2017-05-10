package kcommands;

public class ConcreteCommand extends KCommand {

	public ConcreteCommand(int priority) {
		super(priority);
	}

	@Override
	public void execute() {
		System.out.println("My priority is #"+this.getPriority());
	}
}
