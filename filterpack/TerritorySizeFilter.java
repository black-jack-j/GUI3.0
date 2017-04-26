package filterpack;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerFilter;

import gui.Territory;

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
		Entry<String,Territory> entry = (Entry<String,Territory>)element;
		boolean b = entry.getValue().getSquare() > searchSize;
		if (isGreater) return b;
		else return !b;
	}

}
