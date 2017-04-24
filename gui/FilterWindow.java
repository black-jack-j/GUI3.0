package gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Scale;

public class FilterWindow {
	private Text TKeyFilter;
	private Text text;
	FilterWindow(Shell parent){
		Shell filterWindow = new Shell(parent, SWT.DIALOG_TRIM| SWT.APPLICATION_MODAL);
		filterWindow.setSize(240, 220);
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
		TKeyFilter.setBounds(110, 10, 114, 25);
		
		text = new Text(filterWindow, SWT.BORDER);
		text.setBounds(110, 41, 114, 25);
		
		Scale scale = new Scale(filterWindow, SWT.NONE);
		scale.setBounds(95, 80, 129, 54);
		
	}
}
