package filterpack;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;

import bdata.KPlace;

public class TerritoryNameFilter extends CustomFilter{
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
		KPlace entry = (KPlace)element;
		return entry.getName().matches(searchString);
	}

}
