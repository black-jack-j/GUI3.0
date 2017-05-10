package kcommands;

import java.io.Serializable;

public abstract class KCommand implements Comparable<KCommand>, Serializable {
	private int priority;
	
	public KCommand(int priority){
		this.priority = priority;
	}
	
	@Override
	public int compareTo(KCommand arg0) {
		return (this.priority-arg0.getPriority());
	}
	public int getPriority(){
		return this.priority;
	}
	
	public abstract void execute();
}
