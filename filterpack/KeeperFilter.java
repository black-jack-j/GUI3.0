package filterpack;

import org.eclipse.jface.viewers.Viewer;
import gui.Note;

public class KeeperFilter extends CustomFilter{
	private String searchString;
	
	@Override
	public void setSearch(String s){
		searchString = (".*" + s + ".*").toUpperCase();
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Note n = (Note)element;
		if (searchString==null||searchString.length()==0) return true;
		else return n.getKeep().getName().toUpperCase().matches(searchString);
	}

}
