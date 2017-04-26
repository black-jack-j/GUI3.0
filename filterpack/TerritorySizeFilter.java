package filterpack;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import gui.Territory;

public class TerritorySizeFilter extends ViewerFilter{
	private double searchSize;
	private boolean isGreater;
	private boolean notEnabled;
	TerritorySizeFilter(double measure){
		isGreater = true;
		searchSize = measure;
		notEnabled = true;
	}
	
	public void setDescending(){
		isGreater = true;
	}
	
	public void setAscending(){
		isGreater = false;
	}
	
	public void setMeasure(double s){
		searchSize = s;
	}
	
	public void setEnabled(){
		notEnabled = !notEnabled;
	}
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		Entry<String,Territory> entry = (Entry<String,Territory>)element;
		boolean b = entry.getValue().getSquare() > searchSize;
		if (isGreater) return notEnabled||b;
		else return notEnabled||!b;
	}

}
