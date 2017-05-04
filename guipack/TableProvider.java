package guipack;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.Text;
import filterpack.CustomFilter;
import utilites.FormDataObject;

public class TableProvider<T> {
	private Composite header;
	private Table table;
	private TableViewer viewer;
	private List<TableViewerColumn> cols;
	private T input;
	TableProvider(IStructuredContentProvider e, T o, Composite parent,int style){
		cols = new ArrayList<>();
		viewer = new TableViewer(parent, style);
		viewer.setContentProvider(e);
		input = o;
		viewer.setInput(input);
		table = viewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
		table.addControlListener(new ControlAdapter(){
			public void controlResized(ControlEvent ce){
				setPretty();
			}
		});
		header = new Composite(parent,SWT.NONE);
		header.setLayout(new FormLayout());
	}
	public TableProvider<T> addColumns(ColumnComparator comparators, ColumnLabelProvider[] providers,String[] names){
		addColumns(providers,names);
		for(int i =0;i<names.length;i++){
			cols.get(i).getColumn().addSelectionListener(getSelectionAdapter(comparators, cols.get(i), i));
		}
		viewer.setComparator(comparators);
		return this;
	}
	public TableProvider<T> addColumns(ColumnComparator comparators, ColumnLabelProvider[] providers,String[] names, int...width){
		addColumns(comparators, providers,names,width);
		for(int i =0;i<names.length;i++){
			cols.get(i).getColumn().setWidth(width[i]);
		}
		return this;
	}
	public TableProvider<T> addColumns(ColumnLabelProvider[] providers, String[] names,int...width){
		addColumns(providers,names);
		for(int i =0;i<names.length;i++){
			cols.get(i).getColumn().setWidth(width[i]);
		}
		return this;
	}
	public TableProvider<T> addColumns(ColumnLabelProvider[] providers, String[] names){
		for(int i =0;i<names.length;i++){
			TableViewerColumn col = new TableViewerColumn(viewer, SWT.NONE);
			col.getColumn().setText(names[i]);
			col.getColumn().setResizable(false);
			col.getColumn().setMoveable(false);
			col.setLabelProvider(providers[i]);
			col.getColumn().pack();
			cols.add(col);
		}
		return this;
	}
	public TableProvider<T> setWidth(int index, int width){
		if(index > 0 && index < cols.size()) cols.get(index).getColumn().setWidth(width);
		return this;
	}
	public TableProvider<T> setWidth(int width){
		cols.forEach((TableViewerColumn tvc)-> tvc.getColumn().setWidth(width));
		return this;
	}
	
	private SelectionAdapter getSelectionAdapter(ColumnComparator comparator, TableViewerColumn t, int index){
		SelectionAdapter selection = new SelectionAdapter(){
			@Override
			public void widgetSelected(SelectionEvent e){
				comparator.setColumn(index);
				int dir = comparator.getOrder();
				table.setSortDirection(dir);
				table.setSortColumn(t.getColumn());
				viewer.refresh();
			}
		};
		return selection;
	}
	
	public TableProvider<T> setPretty(){
		FormData fd = (FormData) viewer.getTable().getParent().getLayoutData();
		FormAttachment fR = fd.right;
		FormAttachment fL = fd.left;
		int width = (fR.numerator-fL.numerator)*viewer.getTable().getShell().getClientArea().width/100;
		if (!cols.isEmpty())setWidth(width/cols.size());
		return this;
	}
	
	public TableProvider<T> setSize(int widthP, int heightP){
		FormData fd = new FormData();
		fd.left = new FormAttachment(0);
		fd.right = new FormAttachment(widthP);
		fd.top = new FormAttachment(0);
		fd.bottom = new FormAttachment(100-heightP);
		header.setLayoutData(fd);
		FormData tmp = new FormData();
		tmp.top = new FormAttachment(100-heightP,0);
		tmp.bottom = new FormAttachment(100);
		tmp.left = new FormAttachment(0);
		tmp.right = new FormAttachment(100);
		table.setLayoutData(tmp);
		return this;
	}
	
	public <F extends CustomFilter> void addSearch(F f){
		Composite outBorder = FormDataObject.getFormedComposite(69, 9, 99, 91, Composite.class, header, SWT.NONE);
		outBorder.setLayout(new FormLayout());
		Composite inBorder = FormDataObject.getFormedComposite(0, 0, 100, 100, Composite.class, outBorder, SWT.NONE);
		inBorder.setLayout(new FormLayout());
		Text search = FormDataObject.getFormedControl(2,7,98,93,Text.class, inBorder ,SWT.SEARCH|SWT.CANCEL|SWT.ICON_SEARCH);
<<<<<<< HEAD
		search.setMessage("search...");
		search.addControlListener(new ControlListener(){

			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void controlResized(ControlEvent arg0) {
				FontData[] fd = search.getFont().getFontData();
				fd[0].setHeight((int) (search.getClientArea().height*0.4));
				search.setFont(new Font(Display.getCurrent(), fd[0]));
			}
			
		});
=======
		search.setMessage("Search...");
>>>>>>> f52a102649bd41b61c007d56fe1a182c69f2f531
		outBorder.setBackground(new Color(Display.getCurrent(),255,255,255));
		inBorder.setBackground(new Color(Display.getCurrent(), 121,121,121));
		search.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				f.setSearch(search.getText());
				viewer.refresh();
			}
			
		});
		viewer.addFilter(f);
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
	public Composite getHeader(){
		return header;
	}
	
	public TableViewerColumn getColumn(int i){
		return cols.get(i);
	}
	public void addFilter(CustomFilter cs){
		viewer.addFilter(cs);
	}
}
