package functions;

import gui.Note;

public class KeeperSaver implements Runnable{
	private Note note;
	public KeeperSaver(Note n){
		this.note = n; 
	}
	
	@Override
	public void run() {
		synchronized(note){
			note.save(); 
		}
	}

}
