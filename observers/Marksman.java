package observers;

import gui.KeeperController;
import gui.Note;

public class Marksman {
	private KeeperController overlord;
	public Marksman(KeeperController kc){
		overlord = kc;
	}
	public void createRequest(String name){
		overlord.addKeeper(name);
	}
	public void loadRequest(String path){
		overlord.loadKeeper(path);
	}
	public void deleteRequest(Note n){
		overlord.removeNote(n);
	}
	public void createTerritoryRequest(String key, String size, String square){
		
	}
	public void deleteTerritoryRequest(){
		
	}
	public void deleteLowerRequest(){
		
	}
	
}
