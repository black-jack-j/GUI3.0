package gui;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import tevents.TerritoryAddEvent;
import tevents.TerritoryListener;
import tevents.TerritoryRemoveEvent;

public class Note implements Comparable<Note>{
	private Path path;
	private Keeper keep;
	private List<TerritoryListener> territoryListeners;
	public Note(Path p, Keeper k){
		path = p;
		keep = k;
		territoryListeners = new ArrayList<>();
	}
	@Override
	public boolean equals(Object o){
		return path.equals(((Note)o).getPath());
	}
	public Path getPath(){
		return path;
	}
	public void setPath(Path p){
		this.path = p;
	}
	public Keeper getKeep(){
		return keep;
	}
	@Override
	public int compareTo(Note arg0) {
		return path.compareTo(arg0.getPath());
	}
	public void save(){
		File f = new File(path.toString());
		try {
			f.createNewFile();
			boolean isWritable = f.canWrite();
			f.setWritable(true);
			keep.save(f);
			f.setWritable(isWritable);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void addTerritoryListener(TerritoryListener tl){
		this.territoryListeners.add(tl);
	}
	public void AddTerritoryListener(TerritoryListener...listeners){
		for(TerritoryListener tl:listeners) addTerritoryListener(tl);
	}
	public void AddTerritoryListener(List<TerritoryListener> listeners){
		listeners.forEach((TerritoryListener tl)->addTerritoryListener(tl));
	}
	public void removeTerritoryListeners(TerritoryListener tl){
		this.territoryListeners.remove(tl);
	}
	public void territoryCreated(TerritoryAddEvent tae){
		this.territoryListeners.forEach((TerritoryListener tl)->tl.territoryCreated(tae));
	}
	public void territoryRemoved(TerritoryRemoveEvent tre){
		this.territoryListeners.forEach((TerritoryListener tl)->tl.territoryRemoved(tre));
	}
}
