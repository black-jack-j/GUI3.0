package guipack;

import utilites.FormDataObject;

import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

import bdata.KMap;
import bdata.KPlace;
import filterpack.TerritoryKeyFilter;
import filterpack.TerritoryNameFilter;
import filterpack.TerritorySizeFilter;
import gui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

public class AppTester {
	private Point minSize;
	protected Shell shell;
	protected TableProvider<KMap[]> maps;
	protected TableProvider<KPlace[]> places;
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
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
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
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		mainScreen.setLayout(new FormLayout());

		LFilter = FormDataObject.getFormedControl(15, 4, 85, 34, Label.class, navButtons, SWT.NONE);
		LFilter.setBackground(deepGrey);
		LFilter.setVisible(false);
		
		LInsert = FormDataObject.getFormedControl(15, 36, 85, 66, Label.class, navButtons, SWT.NONE);
		LInsert.setBackground(darkGrey);
		LInsert.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){
				AddShell form = new AddShell(shell, 400, 200);
			}
		});
		LInsert.setVisible(false);
		
		editor = FormDataObject.getFormedControl(15,68,85,98, Label.class, navButtons, SWT.NONE);
		editor.setBackground(white);
		editor.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){
				LFilter.setVisible(!LFilter.getVisible());
				LInsert.setVisible(!LInsert.getVisible());
			}
		});
		editor.setVisible(false);
		//editor.setImage(new Image(Display.getCurrent(),"C:/users/fitisovdmtr/Documents/lab6styles/images/ArrowUp.png"));
		
		Menu mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);
		
		filterWindow.setLayout(new FormLayout());
		filterWindow.setBackground(new Color(Display.getCurrent(), 240, 240, 240));
		
		Composite filterHeader = FormDataObject.getFormedComposite(0, 0, 100, 17, Composite.class, filterWindow, SWT.NONE);
		
		filterHeader.setBackground(new Color(Display.getCurrent(), 40, 111, 195));

		Text keyFilter = FormDataObject.getFormedControl(15, 20, 85, 33, Text.class, filterWindow, SWT.NONE);
		keyFilter.setMessage("Filter by key...");
		TerritoryKeyFilter tkf = new TerritoryKeyFilter();
		keyFilter.addControlListener(new ControlListener(){

			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void controlResized(ControlEvent arg0) {
				FontData[] fd = keyFilter.getFont().getFontData();
				fd[0].setHeight((int) (mainScreen.getClientArea().height*0.015));
				keyFilter.setFont(new Font(Display.getCurrent(), fd[0]));
			}
			
		});
		TerritoryNameFilter tnf = new TerritoryNameFilter();
		
		Text nameFilter = FormDataObject.getFormedControl(15, 35, 85, 48, Text.class, filterWindow, SWT.NONE);
		nameFilter.setMessage("Filter by name...");
		nameFilter.addControlListener(new ControlListener(){

			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void controlResized(ControlEvent arg0) {
				FontData[] fd = nameFilter.getFont().getFontData();
				fd[0].setHeight((int) (mainScreen.getClientArea().height*0.015));
				nameFilter.setFont(new Font(Display.getCurrent(), fd[0]));
			}
			
		});
		TerritorySizeFilter tsf = new TerritorySizeFilter();
		
		Text sizeFilter = FormDataObject.getFormedControl(15, 50, 85, 63, Text.class, filterWindow, SWT.NONE);
		sizeFilter.setMessage("Filter by square...");
		sizeFilter.addModifyListener(new ModifyListener(){

			@Override
			public void modifyText(ModifyEvent arg0) {
				tsf.setMeasure(sizeFilter.getText());
			}
			
		});
		
		sizeFilter.addControlListener(new ControlListener(){

			@Override
			public void controlMoved(ControlEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void controlResized(ControlEvent arg0) {
				FontData[] fd = sizeFilter.getFont().getFontData();
				fd[0].setHeight((int) (mainScreen.getClientArea().height*0.015));
				sizeFilter.setFont(new Font(Display.getCurrent(), fd[0]));
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
				KeeperInc tmp = new KeeperInc(shell);
			}
		});
		mNew.setText("New");
		
		MenuItem mLoad = new MenuItem(menu_1, SWT.NONE);
		mLoad.setText("Open...");
		MenuItem mntmSave = new MenuItem(menu_1, SWT.NONE);
		mntmSave.setText("Save");
		
		MenuItem mntmSaveAs = new MenuItem(menu_1, SWT.NONE);
		mntmSaveAs.setText("Save As...");
		
		MenuItem mExit = new MenuItem(menu_1, SWT.NONE);
		mExit.setText("Exit");
		
		MenuItem mntmEdit = new MenuItem(mainMenu, SWT.CASCADE);
		mntmEdit.setText("Edit");
		
		Menu menu = new Menu(mntmEdit);
		mntmEdit.setMenu(menu);
		
		MenuItem mntmPath = new MenuItem(menu, SWT.NONE);
		mntmPath.setText("Path...");
		mExit.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				shell.dispose();
			}
		});
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
	}
}
