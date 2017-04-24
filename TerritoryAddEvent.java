import java.util.EventListener;
import java.util.EventObject;

public class TerritoryAddEvent extends EventObject{
	public TerritoryAddEvent(Object source){
		super(source);
	}
}
class TerritoryRefreshEvent extends EventObject{

	public TerritoryRefreshEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
}
class TerritoryRemoveEvent extends EventObject{
	enum Mode{
		remove, removeLower;
	}
	private String key;
	Mode m;
	public TerritoryRemoveEvent(Object source, String k) {
		super(source);
		this.key = k;
	}
	String getKey(){
		return key;
	}
}
class TerriotrySelectionEvent extends EventObject{

	public TerriotrySelectionEvent(Object source) {
		super(source);
		// TODO Auto-generated constructor stub
	}
	
}
interface TerritoryListener extends EventListener{
	public void territoryCreated(TerritoryAddEvent tae);
	
	public void territoryRemoved(TerritoryRemoveEvent tre);
}
