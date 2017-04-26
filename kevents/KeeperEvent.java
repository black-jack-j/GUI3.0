package kevents;

import java.util.EventObject;

import gui.KeepModel;
import gui.Note;

public class KeeperEvent extends EventObject{
	private KeepModel km;
	private Note n;
	public KeeperEvent(Object source, KeepModel k) {
		super(source);
		this.km = k;
	}
	public KeeperEvent(Object source, Note k) {
		super(source);
		this.n = k;
	}
	KeepModel getKeep(){
		return km;
	}
}
