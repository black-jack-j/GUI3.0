package kevents;

import java.util.EventObject;

public class KeeperAddEvent extends EventObject {
	public enum Mode{
		create, load;
	}
	public Mode m;
	private String s;
	public KeeperAddEvent(Object source,String tmp) {
		super(source);
		s = tmp;
	}
	public String getMessage(){
		return s;
	}
}