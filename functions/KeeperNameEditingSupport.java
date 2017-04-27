package functions;
import java.util.Map.Entry;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import gui.Territory;

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
		return ((Entry<String, Territory>)arg0).getValue().getName();
	}
	@Override
	protected void setValue(Object arg0, Object arg1) {
		((Entry<String, Territory>)arg0).getValue().setName(String.valueOf(arg1));
		viewer.update(arg0, null);
	}
}
