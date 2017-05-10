package kcommands;

import org.eclipse.jface.viewers.TableViewer;

public abstract class ClientCommand extends KCommand {
	
	protected TableViewer viewer;
	
	public ClientCommand(int priority) {
		super(priority);
	}

	@Override
	public abstract void execute();
	
	public void setViewer(TableViewer viewer){
		this.viewer = viewer;
	}
}
