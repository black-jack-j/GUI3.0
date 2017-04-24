import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

public class TerritoryNameFilter extends ViewerFilter{
	private String searchString;
	private boolean notEnabled = true;
	public void setSearch(String s){
		this.searchString = ".*" + s + ".*";
		notEnabled = true;
	}
	public void setEnabled(){
		notEnabled = !notEnabled;
	}
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (this.searchString==null||this.searchString.length()==0) return true;
		Entry<String,Territory> entry = (Entry<String,Territory>)element;
		return notEnabled||entry.getValue().getName().matches(searchString);
	}

}
