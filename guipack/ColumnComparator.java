package guipack;

import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;

abstract class ColumnComparator extends ViewerComparator{
	protected int propertyIndex;
	private final int DECENTED = 1;
	protected int order;
	ColumnComparator(){
		System.out.println("yeap");
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

