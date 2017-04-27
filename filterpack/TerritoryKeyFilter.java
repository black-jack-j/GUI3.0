package filterpack;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;
import gui.Territory;

public class TerritoryKeyFilter extends CustomFilter{
	private String searchString;
	
	public void setSearch(String s){
		this.searchString = ".*" + s + ".*";
	}
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (this.searchString==null||this.searchString.length()==0) return true;
		Entry<String,Territory> entry = (Entry<String,Territory>)element;
		return entry.getKey().matches(searchString);
	}
}
