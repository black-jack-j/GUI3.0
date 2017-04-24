package guipack;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

import gui.*;

abstract class ColumnComparator<T> extends ViewerComparator{
	protected int propertyIndex;
	private final int DECENTED = 1;
	protected int order;
	ColumnComparator(){
		order = DECENTED;
	}
	public void setColumn(int colIndex){
		if(colIndex==propertyIndex){
			propertyIndex = colIndex;
			order = 1 - order;
		}else{
			propertyIndex = colIndex;
			order = DECENTED;
		}
	}
	public int getOrder(){
		return order;
	}
	
	protected int compareStrings(String s1, String s2){
		int len = s1.length() < s2.length() ? s1.length() : s2.length();
		for(int i=0;i<len;i++){
			int res = 0;
			if((res=(s1.charAt(i)-s2.charAt(i)))!=0) return res;
			else continue;
		}
		return 0;
	}
	protected <T extends Number> int compareNum(T o1, T o2){
		return (o1.intValue() - o2.intValue());
	}
	public int getDirection(){
		return (order==1) ? SWT.UP : SWT.DOWN;
	}
}

class KeeperColumn<KeeperController> extends ColumnComparator<KeeperController>{
	@Override
	public int compare(Viewer view, Object o1, Object o2){
		Entry<String, Territory> entry1 = (Entry<String, Territory>) o1;
		Entry<String, Territory> entry2 = (Entry<String, Territory>) o2;
		int rc = 0;
 		switch(propertyIndex){
 		case 0:{
 			rc = compareStrings(entry1.getKey(),entry2.getKey());
 			break;
 		}
 		case 1:{
 			rc = compareStrings(entry1.getValue().getName(),entry2.getValue().getName());
 			break;
 		}
 		case 2:{
 			rc = compareNum(entry1.getValue().getSquare(),entry2.getValue().getSquare());
 			break;
 		}
 		default : rc = 0;
 		}
		if (order==1) rc = -rc;
		return rc;
	}
}