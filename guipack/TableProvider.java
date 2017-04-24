package guipack;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerComparator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.internal.dialogs.ViewComparator;

public class TableProvider<T> {
	private TableViewer viewer;
	private List<TableViewerColumn> cols;
	private T input;
	TableProvider(IStructuredContentProvider e, T o, Composite parent,int style){
		cols = new ArrayList<>();
		viewer = new TableViewer(parent, style);
		viewer.setContentProvider(e);
		input = o;
		viewer.setInput(input);
	}
	public void addColumns(ColumnComparator comparators, ColumnLabelProvider[] providers,String[] names, int...width){
		for(int i =0;i<names.length;i++){
			TableViewerColumn col = new TableViewerColumn(viewer, SWT.NONE);
			col.getColumn().setText(names[i]);
			col.getColumn().setResizable(false);
			col.getColumn().setMoveable(false);
			col.getColumn().setWidth(width[i]);
			col.getColumn().addSelectionListener(getSelectionAdapter(comparators, col.getColumn(), i));
			col.setLabelProvider(providers[i]);
			cols.add(col);
		}
	}
	public void addColumns(String[] names, ColumnLabelProvider[] providers, int...width){
		for(int i =0;i<names.length;i++){
			TableViewerColumn col = new TableViewerColumn(viewer, SWT.NONE);
			col.getColumn().setText(names[i]);
			col.getColumn().setResizable(false);
			col.getColumn().setMoveable(false);
			col.getColumn().setWidth(width[i]);
			col.setLabelProvider(providers[i]);
			cols.add(col);
		}
	}
	private SelectionAdapter getSelectionAdapter(ColumnComparator comparator, TableColumn t, int index){
		SelectionAdapter selection = new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				comparator.setColumn(index);
				int dir = comparator.getOrder();
				viewer.getTable().setSortDirection(dir);
				viewer.getTable().setSortColumn(t);
				viewer.refresh();
			}
		};
		return selection;
	}
	public T getModel(){
		return input;
	}
	
	public void setModel(T o){
		this.input = o;
	}
	public Table getTable(){
		return viewer.getTable();
	}
	public TableViewer getViewer(){
		return viewer;
	}
}
