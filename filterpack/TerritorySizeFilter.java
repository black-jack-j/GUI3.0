package filterpack;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import bdata.KPlace;

public class TerritorySizeFilter extends ViewerFilter{
	private double searchSize;
	private boolean isGreater;
	public TerritorySizeFilter(){
		isGreater = true;
	}
	
	public void setDescending(){
		isGreater = true;
	}
	
	public void setAscending(){
		isGreater = false;
	}
	
	public void setMeasure(String s){
		try{
			double d = Double.parseDouble(s);
			searchSize = d;
		}catch(NumberFormatException e){
			return;
		}
	}
	
	
	@Override
	public boolean select(Viewer viewer, Object parentElement, Object element) {
		KPlace entry = (KPlace)element;
		boolean b = Double.parseDouble(entry.getSquare()) > searchSize;
		if (isGreater) return b;
		else return !b;
	}

}
