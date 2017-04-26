package gui;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import tevents.*;

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
	public Keeper getKeeper(){
		return keep;
	}
	public void removeTerritory(String key){
		keep.removeComponent(key);
	}
	public void removeLower(String key){
		keep.removeLower(keep.getComponent(key));
	}
	public Map<String, Territory> getTerr(){
		return this.keep.getCollection();
	}

	public void removeComponent(String s) {
		keep.removeComponent(s);
	}
}

