import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.EventListener;
import java.util.EventObject;
class KeeperEvent extends EventObject{
	private KeepModel km;
	public KeeperEvent(Object source, KeepModel k) {
		super(source);
		this.km = k;
	}
	KeepModel getKeep(){
		return km;
	}
}
public class KeeperAddEvent extends EventObject {
	enum Mode{
		create, load;
	}
	Mode m;
	private String s;
	public KeeperAddEvent(Object source,String tmp) {
		super(source);
		s = tmp;
	}
	String getMessage(){
		return s;
	}
}
class KeeperRefreshEvent extends EventObject{
	public KeeperRefreshEvent(Object source) {
		super(source);
	}
}
class KeeperRemoveEvent extends EventObject{
	private int index;
	public KeeperRemoveEvent(Object source, int i) {
		super(source);
		index = i;
	}
	int getIndex(){
		return index;
	}
}
class KeeperSelectionEvent extends KeeperEvent{
	private KeepModel keep;
	public KeeperSelectionEvent(Object source, KeepModel k) {
		super(source, k);
	}
	public KeepModel getKeep(){
		return keep;
	}
	
}
interface KeeperListener extends EventListener{
	public void keeperCreated(KeeperAddEvent kae);
	
	public void keeperReaded(KeeperSelectionEvent kse);
	
	public void keeperUpdated(KeeperRefreshEvent kre);
	
	public void keeperDeleted(KeeperRemoveEvent kre);
}