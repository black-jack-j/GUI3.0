package guipack;

import java.nio.file.Paths;
import java.util.Map.Entry;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import gui.*;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.widgets.Menu;

public class AppTester {
	private TableProvider<KeeperController> keepView;
	private TableProvider<Note> territoryView;
	private KeeperController model;
	private Note keepModel;
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
	}

	/**
	 * Create contents of the window.
	 */
	protected void createContents() {
		model = new KeeperController();
		keepModel = new Note(Paths.get(""),new KeepModel(new Keeper("")));
		shell = new Shell();
		shell.setMinimumSize(new Point(1101, 775));
		shell.setSize(1101, 775);
		shell.setText("SWT Application");
		
		Composite mainScreen = new Composite(shell, SWT.NONE);
		mainScreen.setLayout(new FormLayout());
		
		Composite leftTable = new Composite(mainScreen, SWT.NONE);
		leftTable.setLayout(new FormLayout());
		FormData fd_leftTable = new FormData();
		fd_leftTable.height = 560;
		fd_leftTable.width = 464;
		fd_leftTable.top = new FormAttachment(0, 119);
		fd_leftTable.left = new FormAttachment(0, 20);
		leftTable.setLayoutData(fd_leftTable);
		
		Composite rightTable = new Composite(mainScreen, SWT.NONE);
		FormData fd_rightTable = new FormData();
		fd_rightTable.height = 560;
		fd_rightTable.width = 306;
		fd_rightTable.left = new FormAttachment(100, -332);
		fd_rightTable.bottom = new FormAttachment(leftTable, 0, SWT.BOTTOM);
		fd_rightTable.right = new FormAttachment(100, -20);
		fd_rightTable.top = new FormAttachment(0, 119);
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
		keepView.addColumns(new String[]{"name", "size"},providers, 153, 153);
		Table tableK = keepView.getTable();
		keepView.getViewer().refresh();
		
		FormData fd_right = new FormData();
		fd_right.width = fd_rightTable.width;
		fd_right.height = fd_rightTable.height - 32;
		fd_right.left = new FormAttachment(0,0);
		fd_right.top = new FormAttachment(0,32);
		
		tableK.setLayoutData(fd_right);
		
		tableK.addSelectionListener(new SelectionAdapter(){
			
			@Override
			public void widgetSelected(SelectionEvent se){
				IStructuredSelection s = keepView.getViewer().getStructuredSelection();
				Note n = (Note)s.getFirstElement();
				System.out.println(n.getKeep().getName());
			}
		});
		
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));
		
		
		Menu mainMenu = new Menu(shell, SWT.BAR);
		shell.setMenuBar(mainMenu);
		
		territoryView = new TableProvider<Note>(new KeepContentProvider(),keepModel,leftTable, SWT.FULL_SELECTION);
		
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
		territoryView.addColumns(new KeeperColumn(), providers, new String[]{"key, name, square"}, 153, 153, 153);
		Table tableT = territoryView.getTable();
		tableT.setHeaderVisible(true);
		tableT.setLinesVisible(true);
		FormData fd = new FormData();
		fd.width = 464;
		fd.height = 528;
		fd.left = new FormAttachment(0,0);
		fd.top = new FormAttachment(0,32);
		tableT.setLayoutData(fd);
	}
}
