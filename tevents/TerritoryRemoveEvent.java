package tevents;

import java.util.EventObject;

public class TerritoryRemoveEvent extends EventObject{
	public enum Mode{
		remove, removeLower;
	}
	private String key;
	public Mode m;
	public TerritoryRemoveEvent(Object source, String k) {
		super(source);
		this.key = k;
	}
	public String getKey(){
		return key;
	}
}
