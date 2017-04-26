package guipack;

import kevents.*;
import tevents.TerritoryAddEvent;
import tevents.TerritoryListener;
import tevents.TerritoryRemoveEvent;
import utilites.FormDataObject;

import java.io.IOException;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.DoubleClickEvent;
import org.eclipse.jface.viewers.IDoubleClickListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import filterpack.KeeperFilter;
import gui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.custom.CLabel;

public class AppTester {
	private TableProvider<KeeperController> keepView;
	private TableProvider<Note> territoryView;
	private KeeperController model;
	private Point minSize;
	protected Shell shell;
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			AppTester window = new AppTester();
			window.open();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open() {
		Display display = Display.getDefault();
		createContents();
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		model.close();
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		try {
			TerritoryListener tmp = new TerritoryListener(){

				@Override
				public void territoryCreated(TerritoryAddEvent tae) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void territoryRemoved(TerritoryRemoveEvent tre) {
					// TODO Auto-generated method stub
					
				}
				
			};
			model = new KeeperController("C:/users/fitisovdmtr/lab/config.xml", tmp);
		} catch (IOException e) {
			System.out.println("failed to connect to config file");
			e.printStackTrace();
		}
		model.addKeeperListener(new KeeperListener(){

			@Override
			public void keeperCreated(KeeperAddEvent kae) {
				
			}

			@Override
			public void keeperReaded(KeeperSelectionEvent kse) {
				territoryView.getViewer().setInput(kse.getNote());
				territoryView.getViewer().refresh();
				keepView.getViewer().refresh();
			}

			@Override
			public void keeperUpdated(KeeperRefreshEvent kre) {
				
			}

			@Override
			public void keeperDeleted(KeeperRemoveEvent kre) {
				
			}
			
		});
		shell = new Shell();
		
		Composite mainScreen = new Composite(shell, SWT.NONE);
		Composite leftTable = new Composite(mainScreen, SWT.NONE);
		Composite rightTable = new Composite(mainScreen, SWT.NONE);
		
		shell.setMinimumSize(new Point(640, 480));
		minSize = shell.getMinimumSize();
		minSize.y = 720;
		shell.setSize(640, 480);
		
		/**shell.addControlListener(new ControlAdapter() {
			@Override
			public void controlResized(ControlEvent e) {
				if(shell.getSize().x <= minSize.x & shell.getSize().y >= minSize.y){
					FormData fd = new FormData();
					fd.top = new FormAttachment(1);
					fd.left = new FormAttachment(1);
					fd.right = new FormAttachment(99);
					fd.bottom = new FormAttachment(19);
					rightTable.setLayoutData(fd);
					
					FormData tmp = new FormData();
					tmp.top = new FormAttachment(20);
					tmp.left = new FormAttachment(1);
					tmp.right = new FormAttachment(99);
					tmp.bottom = new FormAttachment(99);
					leftTable.setLayoutData(tmp);
					
				}else{
					FormData fd_rightTable = new FormData();
					fd_rightTable.bottom = new FormAttachment(99);
					fd_rightTable.right = new FormAttachment(99);
					fd_rightTable.top = new FormAttachment(15);
					fd_rightTable.left = new FormAttachment(61);
					rightTable.setLayoutData(fd_rightTable);
					
					FormData fd_leftTable = new FormData();
					fd_leftTable.top = new FormAttachment(15);
					fd_leftTable.left = new FormAttachment(1);
					fd_leftTable.right = new FormAttachment(60);
					fd_leftTable.bottom = new FormAttachment(99);
					leftTable.setLayoutData(fd_leftTable);
					
				}
			}
		});**/
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		FormLayout fl_mainScreen = new FormLayout();
		mainScreen.setLayout(fl_mainScreen);
		
		FormData fd_leftTable = new FormData();
		fd_leftTable.top = new FormAttachment(15);
		fd_leftTable.left = new FormAttachment(1);
		fd_leftTable.right = new FormAttachment(40);
		fd_leftTable.bottom = new FormAttachment(99);
		leftTable.setLayoutData(fd_leftTable);
		leftTable.setLayout(new FormLayout());
		
		rightTable.setLayout(new FormLayout());
		FormData fd_rightTable = new FormData();
		fd_rightTable.bottom = new FormAttachment(99);
		fd_rightTable.right = new FormAttachment(99);
		fd_rightTable.top = new FormAttachment(15);
		fd_rightTable.left = new FormAttachment(61);
		rightTable.setLayoutData(fd_rightTable);
		
		keepView = new TableProvider<KeeperController>(new KeeperContentProvider(), model, rightTable, SWT.FULL_SELECTION);
		ColumnLabelProvider[] providers = new ColumnLabelProvider[]{
				new ColumnLabelProvider(){
					public String getText(Object o){
						Note km = (Note) o;
						return km.getKeep().getName();
					}
				},
				new ColumnLabelProvider(){
					public String getText(Object o){
						Note km = (Note) o;
						return km.getKeep().getSize();
					}
				}
		};
		
		keepView.addColumns(providers, new String[]{"name", "size"}).setPretty().setSize(100, 94);
		
		keepView.getTable().addSelectionListener(new SelectionAdapter(){
			
			@Override
			public void widgetSelected(SelectionEvent se){
				IStructuredSelection s = keepView.getViewer().getStructuredSelection();
				Note n = (Note)s.getFirstElement();
				KeeperSelectionEvent kse = new KeeperSelectionEvent(se, n);
				model.keeperReaded(kse);
			}
		});
		
		
		Menu mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);
		
		territoryView = new TableProvider<Note>(new KeepContentProvider(),null,leftTable, SWT.FULL_SELECTION);
		
		Composite filterWindow = new Composite(mainScreen, SWT.NONE);
		filterWindow.setLayout(new FormLayout());
		Text text = FormDataObject.getFormedControl(10, 5, 90, 20, Text.class, filterWindow, SWT.NONE);
		
		FormData fd_composite = new FormData();
		fd_composite.left = new FormAttachment(41);
		fd_composite.top = new FormAttachment(leftTable, 0, SWT.TOP);
		fd_composite.right = new FormAttachment(60);
		fd_composite.bottom = new FormAttachment(99);
		filterWindow.setLayoutData(fd_composite);
		providers = new ColumnLabelProvider[]{
			new ColumnLabelProvider(){
				@Override
				public String getText(Object o){
					Entry<String,Territory> entry = (Entry<String, Territory>)o;
					return entry.getKey();
				}
			},
			new ColumnLabelProvider(){
				@Override
				public String getText(Object o){
					Entry<String,Territory> entry = (Entry<String, Territory>)o;
					return entry.getValue().getName();
				}
			},
			new ColumnLabelProvider(){
				@Override
				public String getText(Object o){
					Entry<String,Territory> entry = (Entry<String, Territory>)o;
					return String.valueOf(entry.getValue().getSquare());
				}
			}
		};
		
		territoryView.addColumns(new KeeperColumn(),providers, new String[]{"key", "name", "square"}).setPretty().setSize(100, 94);
		territoryView.getHeader().setBackground(new Color(Display.getCurrent(),0,0,0));
		keepView.getHeader().setBackground(new Color(Display.getCurrent(),103,157,246));
		keepView.setSize(100,94).addSearch(new KeeperFilter());
		keepView.getViewer().addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent arg0) {
				IStructuredSelection s = (IStructuredSelection) arg0.getSelection();
				Shell browser = new Shell(SWT.DIALOG_TRIM  & (~SWT.RESIZE));
				browser.setLayout(new FillLayout());
				Browser bros = new Browser(browser,SWT.NONE);
				if(!s.isEmpty()) {
					bros.setUrl("http://maps.google.com/?q="+((Note)s.getFirstElement()).getKeep().getName());
					browser.setSize(640, 480);
					browser.open();
					bros.refresh();
				}
			}
			
		});
		keepView.getViewer().refresh();
	}
}
