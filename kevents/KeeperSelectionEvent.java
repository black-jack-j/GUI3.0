package kevents;

import gui.KeepModel;
import gui.Note;

public class KeeperSelectionEvent extends KeeperEvent{
	private Note keep;
	public KeeperSelectionEvent(Object source, Note k) {
		super(source, k);
		this.keep = k;
	}
	public Note getNote(){
		return keep;
	}
	
}