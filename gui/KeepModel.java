package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class KeepModel{
	private List<TerritoryListener> territoryListeners;
	private Keeper keep;
	public KeepModel(Keeper k){
		this.keep = k;
		territoryListeners = new ArrayList<>();
	}
	
	void addTerritory(String key, Territory t){
		this.keep.addComponent(key, t);
	}
	
	public String getName(){
		return keep.getName();
	}
	public String getSize(){
		return String.valueOf(keep.getCollection().size());
	}
	List<List<String>> getTerritory(){
		List<List<String>> outer = new ArrayList<>();
		keep.getCollection().forEach((String key,Territory t)->{
			List<String> inner = new ArrayList<>();
			inner.add(key);
			inner.add(t.getName());
			inner.add(String.valueOf(t.getSquare()));
			outer.add(inner);
		});
		return outer;
	}
	
	void addTerritoryListener(TerritoryListener tl){
		this.territoryListeners.add(tl);
	}
	void removeTerritoryListeners(TerritoryListener tl){
		this.territoryListeners.remove(tl);
	}
	void territoryCreated(TerritoryAddEvent tae){
		this.territoryListeners.forEach((TerritoryListener tl)->tl.territoryCreated(tae));
	}
	void territoryRemoved(TerritoryRemoveEvent tre){
		this.territoryListeners.forEach((TerritoryListener tl)->tl.territoryRemoved(tre));
	}
	Keeper getKeeper(){
		return keep;
	}
	void removeTerritory(String key){
		keep.removeComponent(key);
	}
	void removeLower(String key){
		keep.removeLower(keep.getComponent(key));
	}
	Map<String, Territory> getTerr(){
		return this.keep.getCollection();
	}

	public void removeComponent(String s) {
		keep.removeComponent(s);
	}
}

