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
class KeepContentProvider extends BaseLabelProvider implements IStructuredContentProvider {
	private Note keep;
	@Override
	public Object[] getElements(Object arg0) {
		return keep.getKeep().getTerr().entrySet().toArray();
	}
	public void inputChanged(Viewer view, Object oldInput, Object newInput){
		keep = (Note)newInput;
	}
}
class KeeperLabelProvider implements ILabelProvider{

	@Override
	public void addListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean isLabelProperty(Object arg0, String arg1) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void removeListener(ILabelProviderListener arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Image getImage(Object arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getText(Object arg0) {
		KeepModel km = (KeepModel)arg0;
		return km.getName() + " " + km.getSize();
	}
	
}