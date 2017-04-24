import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class KeeperFilter extends ViewerFilter{
	private String searchString;
	
	public void setSearch(String search){
		searchString = ".*" + search + ".*";
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (this.searchString==null||this.searchString.length()==0) return true;
		Note n = (Note)element; 
		return n.getKeep().getName().matches(searchString);
	}

}
