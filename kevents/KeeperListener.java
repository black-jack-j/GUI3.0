package kevents;
import java.util.EventListener;
import java.util.EventObject;

public interface KeeperListener extends EventListener{
	public void keeperCreated(KeeperAddEvent kae);
	
	public void keeperReaded(KeeperSelectionEvent kse);
	
	public void keeperUpdated(KeeperRefreshEvent kre);
	
	public void keeperDeleted(KeeperRemoveEvent kre);
}