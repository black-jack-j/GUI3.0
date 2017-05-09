package functions;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import bdata.KPlace;

public class KeeperNameEditingSupport extends EditingSupport{
	private TableViewer viewer;
	private CellEditor cell;
		
	public KeeperNameEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		cell = new TextCellEditor(viewer.getTable());
	}
	@Override
	protected boolean canEdit(Object arg0) {
	// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected CellEditor getCellEditor(Object arg0) {
		// TODO Auto-generated method stub
		return cell;
	}
	@Override
	protected Object getValue(Object arg0) {
		// TODO Auto-generated method stub
		return ((KPlace)arg0).getName();
	}
	@Override
	protected void setValue(Object arg0, Object arg1) {
		if(!String.valueOf(arg1).trim().isEmpty())((KPlace)arg0).setName(String.valueOf(arg1));
		viewer.update(arg0, null);
	}
}
