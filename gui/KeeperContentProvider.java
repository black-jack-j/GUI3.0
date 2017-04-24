package gui;
import org.eclipse.jface.viewers.BaseLabelProvider;
import org.eclipse.jface.viewers.ILabelProvider;
import org.eclipse.jface.viewers.ILabelProviderListener;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.swt.graphics.Image;

public class KeeperContentProvider extends BaseLabelProvider implements IStructuredContentProvider {
	private KeeperController model;
	
	@Override
	public Object[] getElements(Object arg0) {
		return (model.getNStorage()).toArray();
	}
	
	public void inputChanged(Viewer view, Object oldInput, Object newInput){
		this.model = (KeeperController)newInput;
	}
}