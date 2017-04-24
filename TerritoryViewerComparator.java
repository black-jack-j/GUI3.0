import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

public class TerritoryViewerComparator extends ViewerComparator {
	private int propertyIndex;
	private static final int DECENDING = 1;
	private int direction;
	
	public TerritoryViewerComparator(){
		this.propertyIndex = 0;
		direction = DECENDING;
	}
	
	public int getDirection(){
		return direction == 1 ? SWT.UP : SWT.DOWN;
	}
	public void setColumn(int col){
		if (propertyIndex==col){
			direction=1-direction;
		}else{
			propertyIndex = col;
			direction = DECENDING;
		}
	}
	@Override
	public int compare(Viewer v, Object o1, Object o2){
		Entry<String,Territory> entry = (Entry<String,Territory>)o1;
		Entry<String,Territory> entry2 = (Entry<String,Territory>)o2;
		int rc = 0;
		switch(propertyIndex){
		case 0:{
			String s1 = entry.getKey();
			String s2 = entry2.getKey();
			int length = s2.length()>s1.length() ? s1.length() : s2.length(); 
			for(int i = 0;i<length;i++){
				if (s1.charAt(i)!=s2.charAt(i)) {
					rc = s1.charAt(i)-s2.charAt(i);
					break;
				}
				else rc = 0;
			}
			break;
		}
		case 1:{
			String s1 = entry.getValue().getName();
			String s2 = entry2.getValue().getName();
			int length = s2.length()>s1.length() ? s1.length() : s2.length(); 
			for(int i = 0;i<length;i++){
				if (s1.charAt(i)!=s2.charAt(i)) {
					rc = s1.charAt(i)-s2.charAt(i);
					break;
				}
				else rc = 0;
			}
			break;
		}
		case 2:{
			rc = (int) (entry.getValue().getSquare() - entry2.getValue().getSquare());
			break;
		}
		default: rc = 0;
		}
		if (direction==DECENDING) rc = -rc;
		return rc;
	}
	
}
