package filterpack;

import org.eclipse.jface.viewers.Viewer;

import bdata.KPlace;

public class TerritoryKeyFilter extends CustomFilter{
	private String searchString;
	
	public void setSearch(String s){
		this.searchString = ".*" + s + ".*";
	}
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		if (this.searchString==null||this.searchString.length()==0) return true;
		KPlace entry = (KPlace)element;
		return entry.getKey().matches(searchString);
	}
}
