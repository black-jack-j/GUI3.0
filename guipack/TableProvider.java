package guipack;

import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.ui.internal.dialogs.ViewComparator;

public class TableProvider<T> {
	private TableViewer viewer;
	private Table table;
	private List<TableViewerColumn> cols;
	private T input;
	TableProvider(IStructuredContentProvider e, T o, Composite parent,int style){
		viewer = new TableViewer(parent, style);
		viewer.setContentProvider(e);
	}
	public void addColumns(ViewComparator[] comparators,String[] names, int...width){
		for(int i =0;i<names.length;i++){
			TableViewerColumn col = new TableViewerColumn(viewer, SWT.NONE);
			col.getColumn().setText(names[i]);
			col.getColumn().setResizable(false);
			col.getColumn().setMoveable(false);
			col.getColumn().setWidth(width[i]);
		}
	}
	private void
}
