import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;

public class FilterWindow {
	private TerritoryNameFilter tnf;
	private TerritoryKeyFilter tkf;
	private TerritorySizeFilter tsf;
	private Text TKeyFilter;
	private Text text;
	FilterWindow(Shell parent, TableViewer viewer, int min, int max){
		Shell filterWindow = new Shell(parent, SWT.DIALOG_TRIM| SWT.APPLICATION_MODAL);
		filterWindow.setSize(240, 329);
		filterWindow.setBackground(parent.getBackground());
		filterWindow.open();
		
		Label keyFilter = new Label(filterWindow, SWT.NONE);
		keyFilter.setBounds(10, 10, 40, 25);
		keyFilter.setText("key");
		
		Label lblName = new Label(filterWindow, SWT.NONE);
		lblName.setBounds(10, 41, 44, 25);
		lblName.setText("name");
		
		Label lblSize = new Label(filterWindow, SWT.NONE);
		lblSize.setBounds(10, 91, 40, 25);
		lblSize.setText("size");
		
		TKeyFilter = new Text(filterWindow, SWT.BORDER);
		TKeyFilter.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				tkf.setSearch(TKeyFilter.getText());
				viewer.refresh();
			}
		});
		tkf = new TerritoryKeyFilter();
		viewer.addFilter(tkf);
		
		TKeyFilter.setBounds(79, 10, 114, 25);
		
		text = new Text(filterWindow, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				tnf.setSearch(text.getText());
				viewer.refresh();
			}
		});
		tnf = new TerritoryNameFilter();
		viewer.addFilter(tnf);
		
		text.setBounds(79, 41, 114, 25);
		
		Scale scale = new Scale(filterWindow, SWT.NONE);
		scale.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tsf.setMeasure((double)(scale.getSelection()));
				System.out.println(scale.getSelection());
				viewer.refresh();
			}
		});
		tsf = new TerritorySizeFilter(0);
		viewer.addFilter(tsf);
		
		scale.setMinimum(min);
		scale.setMaximum(max);
		scale.setBounds(67, 81, 129, 54);
		
		Button keyB = new Button(filterWindow, SWT.CHECK);
		keyB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tkf.setEnabled();
				viewer.refresh();
			}
		});
		keyB.setBounds(202, 10, 24, 25);
		
		Button nameB = new Button(filterWindow, SWT.CHECK);
		nameB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tnf.setEnabled();
				viewer.refresh();
			}
		});
		nameB.setBounds(202, 41, 24, 25);
		
		Button sizeB = new Button(filterWindow, SWT.CHECK);
		sizeB.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tsf.setEnabled();
				viewer.refresh();
			}
		});
		sizeB.setBounds(202, 91, 24, 25);
		
	}
}
