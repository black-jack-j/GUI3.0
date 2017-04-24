import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Shell;

import java.util.Map.Entry;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.ISelectionChangedListener;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.jface.viewers.SelectionChangedEvent;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.widgets.Scale;

public class App {

	protected Shell shlMarko;
	private Text search;
	private KeeperController model;
	private Note keepModel;
	private Composite KeeperModeView;
	private Label editor;
	private Label LInsert;
	private Label LFilter;
	private Label LSortDis;
	private TableViewer TerritoryViewer;
	private TableViewer KeeperViewer;
	Device dev = Display.getCurrent();
	Color deepGrey = new Color(dev,81,81,81);
	Color white = new Color(dev,255,255,255);
	Color lightGrey = new Color(dev,121,121,121);
	Color darkWhite = new Color(dev,148,148,148);
	Color darkGrey = new Color(dev,107,107,107);
	private KeeperFilter filterK;
	private Table TerritoryView;
	private Table Keepers;
	private Label vSize;
	private Label vName;
	private TerritoryViewerComparator comparator;

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			App window = new App();
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
		shlMarko.open();
		shlMarko.layout();
		while (!shlMarko.isDisposed()) {
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
		comparator = new TerritoryViewerComparator();
		model = new KeeperController();
		model.addKeeperListener(new KeeperListener(){

			@Override
			public void keeperCreated(KeeperAddEvent kae) {
				switch(kae.m){
					case create:{
						model.addKeeper(kae.getMessage());
						break;
					}
					case load:{
						model.loadKeeper(kae.getMessage());
						break;
					}
					default: break;
				}
				App.this.keeperReaded();
			}

			@Override
			public void keeperReaded(KeeperSelectionEvent kse) {
				editor.setVisible(true);
				TerritoryViewer.refresh();
				vName.setText(keepModel.getKeep().getName());
				vSize.setText(String.valueOf(keepModel.getKeep().getSize()));
			}

			@Override
			public void keeperUpdated(KeeperRefreshEvent kre) {
				updateTerritoryView();
			}

			@Override
			public void keeperDeleted(KeeperRemoveEvent kre) {
				TerritoryViewer.refresh();
				KeeperViewer.refresh();
				editor.setVisible(false);
			}
			
		});
		shlMarko = new Shell(SWT.SHELL_TRIM);
		shlMarko.setMinimumSize(new Point(1101, 775));
		shlMarko.setSize(450, 300);
		shlMarko.setText("Marko");
		shlMarko.setBackground(darkWhite);
		shlMarko.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		Composite mainScreen = new Composite(shlMarko, SWT.NONE);
		mainScreen.setBackground(darkWhite);
		mainScreen.setLayout(null);
		
		Composite KeeperView = new Composite(mainScreen, SWT.NONE);
		KeeperView.setBounds(20, 119, 464, 560);
		
		LFilter = new Label(KeeperView, SWT.NONE);
		LFilter.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				FilterWindow filter = new FilterWindow(shlMarko,TerritoryViewer,0,100);
			}
		});
		LFilter.setBounds(429, 494, 25, 25);
		LFilter.setVisible(false);
		
		LInsert = new Label(KeeperView, SWT.NONE);
		LInsert.setBounds(398, 494, 25, 25);
		LInsert.setVisible(false);
		
		LSortDis = new Label(KeeperView, SWT.NONE);
		LSortDis.setBounds(398, 525, 25, 25);
		LSortDis.setVisible(false);
		
		editor = new Label(KeeperView, SWT.NONE);
		editor.setBounds(429, 525, 25, 25);
		editor.setVisible(false);
		editor.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){
				LFilter.setVisible(!LFilter.getVisible());
				LInsert.setVisible(!LInsert.getVisible());
				LSortDis.setVisible(!LSortDis.getVisible());
			}
		});
		
		LInsert.addMouseListener(new MouseAdapter(){
			public void mouseDown(MouseEvent e){
				AddShell form = new AddShell(keepModel, shlMarko,400,200);
			}
		});
		
		Composite keepFieldView = new Composite(KeeperView, SWT.NONE);
		keepFieldView.setBackground(lightGrey);
		keepFieldView.setBounds(0, 0, 464, 32);
		
		Label fieldName = new Label(keepFieldView, SWT.NONE);
		fieldName.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		fieldName.setBounds(72, 6, 75, 18);
		fieldName.setBackground(lightGrey);
		fieldName.setForeground(white);
		fieldName.setText("Acitve map:");
		
		Label fieldSize = new Label(keepFieldView, SWT.NONE);
		fieldSize.setText("Current size:");
		fieldSize.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		fieldSize.setBackground(lightGrey);
		fieldSize.setForeground(white);
		fieldSize.setBounds(268, 6, 75, 18);
		
		vName = new Label(keepFieldView, SWT.NONE);
		vName.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		vName.setBackground(lightGrey);
		vName.setForeground(white);
		vName.setBounds(153, 6, 43, 18);
		vName.setText("None");
		
		vSize = new Label(keepFieldView, SWT.NONE);
		vSize.setText("None");
		vSize.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		vSize.setBackground(lightGrey);
		vSize.setForeground(white);
		vSize.setBounds(349, 6, 43, 18);
		
		TerritoryViewer = new TableViewer(KeeperView, SWT.BORDER | SWT.FULL_SELECTION);
		TerritoryViewer.setContentProvider(new KeepContentProvider());
		TerritoryViewer.setInput(keepModel);
		TerritoryView = TerritoryViewer.getTable();
		TerritoryView.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int i = TerritoryView.getSelectionIndex();
				if (i!=-1){
					if(e.button==3){
						Menu terConMenu = new Menu(TerritoryView);
						MenuItem mEdit = new MenuItem(terConMenu,SWT.NONE);
						MenuItem mDelete = new MenuItem(terConMenu,SWT.NONE);
						MenuItem mDeleteLower = new MenuItem(terConMenu,SWT.NONE);
						mDeleteLower.setText("delete lower");
						mDelete.setText("delete");
						mEdit.setText("edit");
						TerritoryView.setMenu(terConMenu);
						mDelete.addSelectionListener(new SelectionAdapter(){
							
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								IStructuredSelection selection = TerritoryViewer.getStructuredSelection();
								String s = ((Entry<String,Territory>) (selection.getFirstElement())).getKey();	
								keepModel.getKeep().removeComponent(s);
								KeeperRefreshEvent kre = new KeeperRefreshEvent(arg0);
								model.keeperUpdated(kre);
							}
							
						});
						mDeleteLower.addSelectionListener(new SelectionAdapter(){
							
							@Override
							public void widgetSelected(SelectionEvent arg0) {
								IStructuredSelection selection = TerritoryViewer.getStructuredSelection();
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
		TerritoryView.setHeaderVisible(true);
		TerritoryView.setLinesVisible(true);
		TerritoryView.setBounds(0, 32, 464, 528);
		
		TableViewerColumn keyCol = new TableViewerColumn(TerritoryViewer, SWT.NONE);
		keyCol.getColumn().setResizable(false);
		keyCol.getColumn().setWidth(153);
		keyCol.getColumn().setText("key");
		
		keyCol.setLabelProvider(new ColumnLabelProvider(){
			public String getText(Object o){
				Entry<String,Territory> entry = (Entry<String, Territory>)o;
					return entry.getKey();
			}
		});	
		TableColumn tmpCol = keyCol.getColumn();
		tmpCol.addSelectionListener(this.getSelectionAdapter(tmpCol, 0));
		
		TableViewerColumn nameCol = new TableViewerColumn(TerritoryViewer, SWT.NONE);
		nameCol.getColumn().setResizable(false);
		nameCol.getColumn().setWidth(154);
		nameCol.getColumn().setText("name");
		
		nameCol.setLabelProvider(new ColumnLabelProvider(){
			public String getText(Object o){
				Entry<String,Territory> entry = (Entry<String, Territory>)o;
				return entry.getValue().getName();
			}
		});
		tmpCol = nameCol.getColumn();
		tmpCol.addSelectionListener(this.getSelectionAdapter(tmpCol, 1));
		
		TableViewerColumn squareCol = new TableViewerColumn(TerritoryViewer, SWT.NONE);
		squareCol.getColumn().setResizable(false);
		squareCol.getColumn().setWidth(153);
		squareCol.getColumn().setText("square");
		
		squareCol.setLabelProvider(new ColumnLabelProvider(){
			public String getText(Object o){
				Entry<String,Territory> entry = (Entry<String, Territory>)o;
				return String.valueOf(entry.getValue().getSquare());
			}
		});
		tmpCol = squareCol.getColumn();
		tmpCol.addSelectionListener(this.getSelectionAdapter(tmpCol, 2));
		
		KeeperModeView = new Composite(mainScreen, SWT.NONE);
		KeeperModeView.setBounds(494, 119, 565, 560);
		KeeperModeView.setLayout(null);
		KeeperModeView.setBackground(lightGrey);
		
		search = new Text(KeeperModeView, SWT.BORDER);
		search.setBounds(475, 4, 80, 24);
		
		KeeperViewer = new TableViewer(KeeperModeView, SWT.BORDER | SWT.FULL_SELECTION);
		KeeperViewer.setContentProvider(new KeeperContentProvider());
		
		TableViewerColumn JNameCol = new TableViewerColumn(KeeperViewer, SWT.NONE);
		JNameCol.setLabelProvider(new ColumnLabelProvider(){
			public String getText(Object o){
				Note km = (Note) o;
				return km.getKeep().getName();
			}
		});
		JNameCol.getColumn().setWidth(200);
		JNameCol.getColumn().setResizable(false);
		JNameCol.getColumn().setText("name");
		
		TableViewerColumn JSizeCol = new TableViewerColumn(KeeperViewer, SWT.NONE);
		JSizeCol.setLabelProvider(new ColumnLabelProvider(){
			public String getText(Object o){
				Note km = (Note) o;
				return km.getKeep().getSize();
			}
		});
		
		JSizeCol.getColumn().setWidth(200);
		JSizeCol.getColumn().setResizable(false);
		JSizeCol.getColumn().setText("size");
		
		search.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				filterK.setSearch(search.getText());
				KeeperViewer.refresh();
			}
		});
		filterK = new KeeperFilter();
		KeeperViewer.addFilter(filterK);
		KeeperViewer.addSelectionChangedListener(new ISelectionChangedListener(){

			@Override
			public void selectionChanged(SelectionChangedEvent arg0) {
				IStructuredSelection selection = (IStructuredSelection)arg0.getSelection();
				keepModel = (Note)selection.getFirstElement();
				TerritoryViewer.setInput(keepModel);
				TerritoryViewer.setComparator(comparator);
				KeeperSelectionEvent kse = new KeeperSelectionEvent(arg0, keepModel.getKeep());
				model.keeperReaded(kse);
			}
			
		});
		
		KeeperViewer.setInput(model);
		
		Keepers = KeeperViewer.getTable();
		Keepers.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				int i = Keepers.getSelectionIndex(); 
				if (i!=-1){
					if(e.button==3){
						Menu keepConMenu = new Menu(Keepers);
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
						Keepers.setMenu(keepConMenu);
						save.addSelectionListener(new SelectionAdapter(){
							public void widgetSelected(SelectionEvent se){
								IStructuredSelection selection = (IStructuredSelection) KeeperViewer.getSelection();
								Note tmp = ((Note)selection.getFirstElement());
								tmp.save();
							}
						});
						saveAs.addSelectionListener(new SelectionAdapter(){
							
							public void widgetSelected(SelectionEvent se){
								FileDialog f = new FileDialog(shlMarko);
								f.setFilterExtensions(new String[]{"*.xml"});
								f.open();
								String s = f.getFilterPath()+"\\" + f.getFileName();
								if (!s.endsWith(".xml")) s+=".xml";
								IStructuredSelection selection = KeeperViewer.getStructuredSelection();
								Note tmp = ((Note)selection.getFirstElement());
								model.markPath(s, tmp);
							}
						});
						delete.addSelectionListener(new SelectionAdapter(){
							
							public void widgetSelected(SelectionEvent se){
								IStructuredSelection selection = (IStructuredSelection) KeeperViewer.getSelection();
								model.removeNote((Note)selection.getFirstElement());
								model.keeperDeleted(new KeeperRemoveEvent(se,i));
								Keepers.deselectAll();
								
							}
						});
						info.addSelectionListener(new SelectionAdapter(){
							public void widgetSelected(SelectionEvent se){
								IStructuredSelection selection = (IStructuredSelection) KeeperViewer.getSelection();
								Note n = (Note)selection.getFirstElement();
								//TO-DO
								System.out.println(n.getPath().toString());
							}
						});
					}
				}
			}
		});
		
		Keepers.setLinesVisible(true);
		Keepers.setHeaderVisible(true);
		Keepers.setBounds(0, 32, 565, 528);
		
		Menu menu = new Menu(shlMarko, SWT.BAR);
		shlMarko.setMenuBar(menu);
		
		MenuItem menuCollection = new MenuItem(menu, SWT.CASCADE);
		menuCollection.setText("Collection");
		
		Menu menu_1 = new Menu(menuCollection);
		menuCollection.setMenu(menu_1);
		
		MenuItem mNew = new MenuItem(menu_1, SWT.NONE);
		mNew.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				KeeperInc tmp = new KeeperInc(shlMarko, model);
			}
		});
		mNew.setText("new map");
		
		MenuItem mLoad = new MenuItem(menu_1, SWT.NONE);
		mLoad.setText("load map");
		mLoad.addSelectionListener(new SelectionAdapter(){
			
			public void widgetSelected(SelectionEvent se){
				FileDialog f = new FileDialog(shlMarko);
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
		mExit.setText("exit");
		mExit.addSelectionListener(new SelectionAdapter(){
			public void widgetSelected(SelectionEvent e){
				shlMarko.dispose();
			}
		});
	}
	void keeperRemove(int i){
		Keepers.remove(i);
	}
	void updateTerritoryView(){
		TerritoryViewer.refresh();
		if(keepModel!=null){
			vName.setText(keepModel.getKeep().getName());
			vSize.setText(String.valueOf(keepModel.getKeep().getSize()));
		}
	}
	void keeperReaded(){
		KeeperViewer.refresh();
		TerritoryViewer.refresh();
		editor.setVisible(true);
	}
	
	private SelectionAdapter getSelectionAdapter(final TableColumn column,
            final int index) {
    SelectionAdapter selectionAdapter = new SelectionAdapter() {
            @Override
            public void widgetSelected(SelectionEvent e) {
                    comparator.setColumn(index);
                    int dir = comparator.getDirection();
                    TerritoryViewer.getTable().setSortDirection(dir);
                    TerritoryViewer.getTable().setSortColumn(column);
                    TerritoryViewer.refresh();
            }
    };
    return selectionAdapter;
}
}
