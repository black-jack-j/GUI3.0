package guipack;

import java.util.Map.Entry;

import org.eclipse.jface.viewers.Viewer;

import gui.Territory;

public class KeeperColumn extends ColumnComparator{
	
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
