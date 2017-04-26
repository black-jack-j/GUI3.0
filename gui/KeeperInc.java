package gui;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;

import kevents.*;

public class KeeperInc {
	private Text FName;
	private KeeperController control;
	KeeperInc(Shell parent,KeeperController tmp){
		Shell window = new Shell(parent, SWT.DIALOG_TRIM|SWT.APPLICATION_MODAL);
		window.setSize(457, 292);
		control = tmp;
		FName = new Text(window, SWT.BORDER);
		FName.setBounds(132, 74, 174, 31);
		
		Button BOk = new Button(window, SWT.NONE);
		BOk.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				KeeperAddEvent kae = new KeeperAddEvent(e,FName.getText());
				kae.m = KeeperAddEvent.Mode.create;
				control.keeperCreated(kae);
				window.dispose();
			}
		});
		BOk.setBounds(55, 199, 105, 35);
		BOk.setText("OK");
		
		Button BCancel = new Button(window, SWT.NONE);
		BCancel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				window.dispose();
			}
		});
		BCancel.setBounds(293, 199, 105, 35);
		BCancel.setText("Cancel");
		window.open();
	}
}
