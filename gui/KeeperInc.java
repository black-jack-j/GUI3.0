package gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class KeeperInc {
	private Text FName;
	public KeeperInc(Shell parent){
		Shell window = new Shell(parent, SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL&(~SWT.RESIZE));
		window.setSize(400, 200);
		
		Label nameFail = new Label(window, SWT.NONE);
		nameFail.setText("incorrect value");
		nameFail.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		nameFail.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		nameFail.setBounds(290, 80, 90, 15);
		nameFail.setVisible(false);

		Label instruction = new Label(window, SWT.NONE);
		instruction.setText("Name of map");
		instruction.setForeground(SWTResourceManager.getColor(SWT.COLOR_BLACK));
		instruction.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		instruction.setBounds(110, 50, 150, 15);
		instruction.setVisible(true);

		FName = new Text(window, SWT.BORDER);
		FName.setBounds(110, 74, 174, 31);
		FName.setText("");
		FName.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
					if(FName.getText().trim().isEmpty()) nameFail.setVisible(true);
					else nameFail.setVisible(false);
			}
		});
		
		Button BOk = new Button(window, SWT.NONE);
		BOk.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if (FName.getText().trim().isEmpty()) { }
				else {
					window.dispose();
				}
			}
		});
		BOk.setBounds(55, 130, 105, 35);
		BOk.setText("OK");
		
		Button BCancel = new Button(window, SWT.NONE);
		BCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				window.dispose();
			}
		});
		BCancel.setBounds(230, 130, 105, 35);
		BCancel.setText("Cancel");
		window.open();
	}
}
