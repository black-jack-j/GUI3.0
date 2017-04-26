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

import kevents.*;

public class KeeperInc {
	private Text FName;
	private KeeperController control;
	public KeeperInc(Shell parent, KeeperController tmp){
		Shell window = new Shell(parent, SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL&(~SWT.RESIZE));
		window.setSize(400, 200);
		
		Label nameFail = new Label(window, SWT.NONE);
		nameFail.setText("incorrect value");
		nameFail.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		nameFail.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		nameFail.setBounds(167, 50, 90, 15);
		nameFail.setVisible(false);
		
		control = tmp;
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
					KeeperAddEvent kae = new KeeperAddEvent(e,FName.getText());
					kae.m = KeeperAddEvent.Mode.create;
					control.keeperCreated(kae);
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
