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
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import filterpack.KeeperFilter;
import filterpack.TerritoryKeyFilter;
import filterpack.TerritoryNameFilter;
import filterpack.TerritorySizeFilter;
import functions.KeeperNameEditingSupport;
import functions.KeeperSizeEditingSupport;
import gui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.browser.Browser;

public class AppTester {
	private TableProvider<KeeperController> keepView;
	private TableProvider<Note> territoryView;
	private KeeperController model;
	private Point minSize;
	protected Shell shell;
	private Note keepModel;
	private boolean isActiveFilter = false;
	private Label editor;
	private Label LInsert;
	private Label LFilter;

	Device dev = Display.getCurrent();
	Color deepGrey = new Color(dev,81,81,81);
	Color white = new Color(dev,255,255,255);
	Color lightGrey = new Color(dev,121,121,121);
	Color darkWhite = new Color(dev,148,148,148);
	Color darkGrey = new Color(dev,107,107,107);
	
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
		
		TerritoryListener tmp = new TerritoryListener(){

			@Override
			public void territoryCreated(TerritoryAddEvent tae) {
				// TODO Auto-generated method stub
				territoryView.getViewer().refresh();
			}

			@Override
			public void territoryRemoved(TerritoryRemoveEvent tre) {
				// TODO Auto-generated method stub
				territoryView.getViewer().refresh();
			}
			
		};
		
		try {
			model = new KeeperController("C:/users/fitisovdmtr/lab/config.xml", tmp);
		} catch (IOException e) {
			System.out.println("failed to connect to config file");
			e.printStackTrace();
		}
		model.addKeeperListener(new KeeperListener(){

			@Override
			public void keeperCreated(KeeperAddEvent kae) {
				switch(kae.m){
				case create:{
					model.addKeeper(kae.getMessage(), tmp);
					break;
				}
				case load:{
					model.loadKeeper(kae.getMessage(),tmp);
					break;
				}
				default: break;
			}
				keepView.getViewer().refresh();
				territoryView.getViewer().refresh();
		}

			@Override
			public void keeperReaded(KeeperSelectionEvent kse) {
				territoryView.getViewer().setInput(kse.getNote());
				territoryView.getViewer().refresh();
				keepView.getViewer().refresh();
			}

			@Override
			public void keeperUpdated(KeeperRefreshEvent kre) {
				territoryView.getViewer().refresh();
			}

			@Override
			public void keeperDeleted(KeeperRemoveEvent kre) {
				keepView.getViewer().refresh();
			}
			
		});
		shell = new Shell();
		
		Composite mainScreen = new Composite(shell, SWT.NONE);
		Composite filterWindow = FormDataObject.getFormedComposite(61, 1, 99, 1, Composite.class, mainScreen, SWT.NONE);
		Composite leftTable = FormDataObject.getFormedComposite(1,1,60,99,Composite.class ,mainScreen, SWT.NONE);
		Composite rightTable = FormDataObject.getFormedComposite(61,new FormAttachment(filterWindow),99,99,Composite.class,  mainScreen, SWT.NONE);
		
		Composite navButtons = FormDataObject.getFormedComposite(90, 78, 98, 98,Composite.class, leftTable, SWT.NONE);
		leftTable.setLayout(new FormLayout());
		rightTable.setLayout(new FormLayout());
		navButtons.setLayout(new FormLayout());
				
		shell.setMinimumSize(new Point(640, 480));
		minSize = shell.getMinimumSize();
		minSize.y = 720;
		shell.setSize(640, 480);
		/*shell.addControlListener(new ControlAdapter() {
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
		});*/
		shell.setText("SWT Application");
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		mainScreen.setLayout(new FormLayout());

		LFilter = FormDataObject.getFormedControl(15, 4, 85, 34, Label.class, navButtons, SWT.NONE);
		LFilter.setBackground(deepGrey);
		LFilter.setVisible(false);
		
		LInsert = FormDataObject.getFormedControl(15, 36, 85, 66, Label.class, navButtons, SWT.NONE);
		LInsert.setBackground(darkGrey);
		LInsert.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){
				AddShell form = new AddShell(keepModel, shell, 400, 200);
			}
		});
		LInsert.setVisible(false);
		
		editor = new Label(navButtons, SWT.NONE);
		editor.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){
				LFilter.setVisible(!LFilter.getVisible());
				LInsert.setVisible(!LInsert.getVisible());
			}
		});
		editor.setVisible(false);
		FormData fd_editor = new FormData();
		fd_editor.top = new FormAttachment(68);
		fd_editor.left = new FormAttachment(15);
		fd_editor.bottom = new FormAttachment(98);
		fd_editor.right = new FormAttachment(85);
		editor.setLayoutData(fd_editor);
		editor.setBackground(white);
		
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
		
		filterWindow.setLayout(new FormLayout());
		filterWindow.setBackground(new Color(Display.getCurrent(), 240, 240, 240));
		
		Composite filterHeader = FormDataObject.getFormedComposite(0, 0, 100, 17, Composite.class, filterWindow, SWT.NONE);
		
		filterHeader.setBackground(new Color(Display.getCurrent(),142,0,0));
		
		Text keyFilter = FormDataObject.getFormedControl(50, 20, 85, 33, Text.class, filterWindow, SWT.NONE);
		
		TerritoryKeyFilter tkf = new TerritoryKeyFilter();
		territoryView.getViewer().addFilter(tkf);
		keyFilter.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				tkf.setSearch(keyFilter.getText());
				territoryView.getViewer().refresh();
			}
			
		});
		
		TerritoryNameFilter tnf = new TerritoryNameFilter();
		
		Text nameFilter = FormDataObject.getFormedControl(50, 35, 85, 48, Text.class, filterWindow, SWT.NONE);
		
		nameFilter.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				tnf.setSearch(nameFilter.getText());
				territoryView.getViewer().refresh();
			}
			
		});
		
		TerritorySizeFilter tsf = new TerritorySizeFilter();
		
		Text sizeFilter = FormDataObject.getFormedControl(50, 50, 85, 63, Text.class, filterWindow, SWT.NONE);
		
		sizeFilter.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				tsf.setMeasure(sizeFilter.getText());
				territoryView.getViewer().refresh();
			}
			
		});
		
		MenuItem menuCollection = new MenuItem(mainMenu, SWT.CASCADE);
		menuCollection.setText("File");
		
		Menu menu_1 = new Menu(menuCollection);
		menuCollection.setMenu(menu_1);
		
		MenuItem mNew = new MenuItem(menu_1, SWT.NONE);
		mNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				KeeperInc tmp = new KeeperInc(shell, model);
			}
		});
		mNew.setText("Create new map");
		
		MenuItem mLoad = new MenuItem(menu_1, SWT.NONE);
		mLoad.setText("Load map");
		mLoad.addSelectionListener(new SelectionAdapter(){
			
			public void widgetSelected(SelectionEvent se){
				FileDialog f = new FileDialog(shell);
				f.setFilterExtensions(new String[]{"*.xml"});
				f.open();
				if (!f.getFileName().isEmpty()){
					String s = f.getFilterPath()+"\\"+f.getFileName();
					KeeperAddEvent kae = new KeeperAddEvent(se,s);
					kae.m = KeeperAddEvent.Mode.load;
					model.keeperCreated(kae);
				}
			}
		});
		
		MenuItem mExit = new MenuItem(menu_1, SWT.NONE);
		mExit.setText("Exit");
		mExit.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				shell.dispose();
			}
		});
		
		keepView.getViewer().addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection)arg0.getSelection();
				keepModel = (Note)selection.getFirstElement();
				territoryView.getViewer().setInput(keepModel);
				keepView.getViewer().refresh();
				editor.setVisible(true);
			}
			
		});
		
		territoryView = new TableProvider<Note>(new KeepContentProvider(),null,leftTable, SWT.FULL_SELECTION);
		territoryView.getViewer().addFilter(tkf);
		territoryView.getViewer().addFilter(tsf);
		LFilter.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseDown(MouseEvent e) {
				
				FormData tmp = (FormData)filterWindow.getLayoutData();
				FormData fd = (FormData)rightTable.getLayoutData();
				
				if (isActiveFilter){
					fd.top = new FormAttachment(filterWindow);
					
					tmp.bottom = new FormAttachment(1);
					isActiveFilter = false;
				}else{
					fd.top = new FormAttachment(filterWindow,10);
					
					tmp.bottom = new FormAttachment(30);
					isActiveFilter = true;
				}
				rightTable.setLayoutData(fd);
				filterWindow.setLayoutData(tmp);
				mainScreen.layout();
			}
		});
		
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
		territoryView.getViewer().addFilter(tnf);
		territoryView.getHeader().setBackground(new Color(Display.getCurrent(),0,0,0));
		keepView.getHeader().setBackground(new Color(Display.getCurrent(),103,157,246));
		keepView.setSize(100,94).addSearch(new KeeperFilter());
		
		territoryView.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				IStructuredSelection selection = territoryView.getViewer().getStructuredSelection();
				if (!selection.isEmpty()){
					if(e.button==3){
						Menu terConMenu = new Menu(territoryView.getTable());
						MenuItem mDelete = new MenuItem(terConMenu,SWT.NONE);
						MenuItem mDeleteLower = new MenuItem(terConMenu,SWT.NONE);
						mDeleteLower.setText("delete lower");
						mDelete.setText("delete");
						territoryView.getTable().setMenu(terConMenu);
						mDelete.addSelectionListener(new SelectionAdapter(){
							
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								String s = ((Entry<String,Territory>) (selection.getFirstElement())).getKey();	
								keepModel.getKeep().removeComponent(s);
								KeeperRefreshEvent kre = new KeeperRefreshEvent(arg0);
								model.keeperUpdated(kre);
							}
							
						});
						mDeleteLower.addSelectionListener(new SelectionAdapter(){
							
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								String tmp = ((Entry<String,Territory>) (selection.getFirstElement())).getKey();	
								keepModel.getKeep().removeLower(tmp);
								KeeperRefreshEvent kre = new KeeperRefreshEvent(arg0);
								model.keeperUpdated(kre);
							}
							
						});
					}
				}
			}
		});
		
		Shell browser = new Shell(SWT.DIALOG_TRIM  & (~SWT.RESIZE));
		browser.setLayout(new FillLayout());
		Browser bros = new Browser(browser,SWT.NONE);
		
		keepView.getTable().addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				IStructuredSelection selection1 = (IStructuredSelection) keepView.getViewer().getSelection();
				Note n1 = (Note) selection1.getFirstElement();
				if (!selection1.isEmpty()){
					if(e.button==3){
						Menu keepConMenu = new Menu(keepView.getTable());
						MenuItem edit = new MenuItem(keepConMenu,SWT.NONE);
						MenuItem save = new MenuItem(keepConMenu,SWT.NONE);
						MenuItem saveAs = new MenuItem(keepConMenu,SWT.NONE);
						MenuItem info = new MenuItem(keepConMenu, SWT.NONE);
						MenuItem delete = new MenuItem(keepConMenu,SWT.NONE);
						edit.setText("edit");
						save.setText("save");
						saveAs.setText("save as");
						info.setText("info");
						delete.setText("delete");
						keepView.getTable().setMenu(keepConMenu);
						save.addSelectionListener(new SelectionAdapter(){
							public void widgetSelected(SelectionEvent se){
								Note tmp = ((Note)selection1.getFirstElement());
								tmp.save();
								System.out.println("File \"" + tmp.getKeep().getName() + "\" was saved at " + tmp.getPath().toString());
							}
						});
						saveAs.addSelectionListener(new SelectionAdapter(){
							
							public void widgetSelected(SelectionEvent se){
								FileDialog f = new FileDialog(shell);
								f.setFilterExtensions(new String[]{"*.xml"});
								f.open();
								String s = f.getFilterPath()+"\\" + f.getFileName();
								if (!s.endsWith(".xml")) s+=".xml";
								Note tmp = ((Note)selection1.getFirstElement());
								model.markPath(s, tmp);
							}
						});
						delete.addSelectionListener(new SelectionAdapter(){
							
							public void widgetSelected(SelectionEvent se){
								model.removeNote((Note)selection1.getFirstElement());
								model.keeperDeleted(new KeeperRemoveEvent(se));
								keepView.getTable().deselectAll();
								
							}
						});
						info.addSelectionListener(new SelectionAdapter(){
							public void widgetSelected(SelectionEvent se){
								
							}
						});
					}
				}
			}
		});
		
		keepView.getViewer().addDoubleClickListener(new IDoubleClickListener(){

			@Override
			public void doubleClick(DoubleClickEvent arg0) {
				IStructuredSelection s = (IStructuredSelection) arg0.getSelection();
				if(!s.isEmpty()) {
					bros.setUrl("http://maps.google.com/?q="+((Note)s.getFirstElement()).getKeep().getName());
					browser.setSize(640, 480);
					if (!browser.isVisible()){
						browser.open();
					}
					System.out.println(((Note)s.getFirstElement()).getKeep().getName());
					bros.refresh();
				}
			}
			
		});
		territoryView.getColumn(1).setEditingSupport(new KeeperNameEditingSupport(territoryView.getViewer()));
		territoryView.getColumn(2).setEditingSupport(new KeeperSizeEditingSupport(territoryView.getViewer()));
		keepView.getViewer().refresh();
	}
}
