package gui;

import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class KeepContentProvider extends BaseLabelProvider implements IStructuredContentProvider {
	private Note keep;
	@Override
	public Object[] getElements(Object arg0) {
		return keep.getKeep().getTerr().entrySet().toArray();
	}
	public void inputChanged(Viewer view, Object oldInput, Object newInput){
		keep = (Note)newInput;
	}
}
