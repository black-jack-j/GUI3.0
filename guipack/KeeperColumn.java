package guipack;

import org.eclipse.jface.viewers.Viewer;

import bdata.KPlace;

public class KeeperColumn extends ColumnComparator{
	
	@Override
	public int compare(Viewer view, Object o1, Object o2){
		KPlace entry1 = (KPlace)o1;
		KPlace entry2 = (KPlace)o2;
		int rc = 0;
 		switch(propertyIndex){
 		case 0:{
 			rc = compareStrings(entry1.getKey(),entry2.getKey());
 			break;
 		}
 		case 1:{
 			rc = compareStrings(entry1.getName(),entry2.getName());
 			break;
 		}
 		case 2:{
 			rc = compareNum(Double.parseDouble(entry1.getSquare()),Double.parseDouble(entry2.getSquare()));
 			break;
 		}
 		default : rc = 0;
 		}
		if (order==1) rc = -rc;
		return rc;
	}
}
