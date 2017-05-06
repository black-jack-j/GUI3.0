package kevents;

import java.util.EventObject;

import gui.Keeper;
import gui.Note;

public class KeeperEvent extends EventObject{
	private Keeper km;
	private Note n;
	public KeeperEvent(Object source, Keeper k) {
		super(source);
		this.km = k;
	}
	public KeeperEvent(Object source, Note k) {
		super(source);
		this.n = k;
	}
	Keeper getKeep(){
		return km;
	}
}
