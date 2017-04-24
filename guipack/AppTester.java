package guipack;

import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;

import gui.*;
import org.eclipse.swt.graphics.Point;

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
		shell = new Shell();
		shell.setMinimumSize(new Point(1101, 775));
		shell.setSize(1101, 765);
		shell.setText("SWT Application");
		keepView = new TableProvider(new KeeperContentProvider(), model, shell, 2);
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
		Table table = keepView.getTable();
		table.setSize(306, 528);
		table.setHeaderVisible(true);
		table.setLinesVisible(true);
	}

}
