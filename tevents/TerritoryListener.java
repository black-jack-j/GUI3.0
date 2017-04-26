package tevents;

import java.util.EventListener;
public interface TerritoryListener extends EventListener{
	
	public void territoryCreated(TerritoryAddEvent tae);
	
	public void territoryRemoved(TerritoryRemoveEvent tre);
}
