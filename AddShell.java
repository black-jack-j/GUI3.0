import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Label;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.KeyAdapter;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class AddShell {
	private Text keyField;
	private Text nameField;
	private Text sizeField;
	AddShell(Note km, Shell parent, int width, int height){
		Shell dialog = new Shell(parent, SWT.DIALOG_TRIM| SWT.APPLICATION_MODAL);
		List<Label> list = new ArrayList<>();
		dialog.setSize(400, 210);
		dialog.setBackground(parent.getBackground());
		
		keyField = new Text(dialog, SWT.BORDER);
		keyField.setBounds(222, 8, 140, 31);
		keyField.setText("");
		
		nameField = new Text(dialog, SWT.BORDER);
		nameField.setBounds(222, 47, 140, 31);
		nameField.setText("");
		sizeField = new Text(dialog, SWT.BORDER);
		sizeField.setBounds(222, 84, 140, 31);
		sizeField.setText("");
		
		Label LKey = new Label(dialog, SWT.NONE);
		LKey.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		LKey.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		LKey.setBounds(21, 16, 39, 25);
		LKey.setText("key");
		list.add(LKey);
		
		Label LName = new Label(dialog, SWT.NONE);
		LName.setText("name");
		LName.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		LName.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		LName.setBounds(21, 53, 39, 25);
		list.add(LName);
		
		Label LSize = new Label(dialog, SWT.NONE);
		LSize.setText("size");
		LSize.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		LSize.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		LSize.setBounds(21, 90, 39, 25);
		list.add(LSize);
		
		Button BCreate = new Button(dialog, SWT.NONE);
		BCreate.setBounds(31, 125, 105, 35);
		BCreate.setText("Ok");
		
		Button BCancel = new Button(dialog, SWT.NONE);
		BCancel.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				dialog.dispose();
			}
		});
		BCancel.setText("Cancel");
		BCancel.setBounds(240, 125, 105, 35);
		
		Label sizeFail = new Label(dialog, SWT.NONE);
		sizeFail.setText("incorrect value");
		sizeFail.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		sizeFail.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		sizeFail.setBounds(99, 90, 117, 25);
		list.add(sizeFail);
		
		Label nameFail = new Label(dialog, SWT.NONE);
		nameFail.setText("incorrect value");
		nameFail.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		nameFail.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		nameFail.setBounds(99, 53, 117, 25);
		list.add(nameFail);
		
		Label keyFail = new Label(dialog, SWT.NONE);
		keyFail.setText("incorrect value");
		keyFail.setForeground(SWTResourceManager.getColor(SWT.COLOR_RED));
		keyFail.setFont(SWTResourceManager.getFont("Segoe UI", 7, SWT.NORMAL));
		keyFail.setBounds(99, 16, 117, 25);
		list.add(keyFail);
		list.forEach((Label l)->l.setBackground(parent.getBackground()));
		sizeFail.setVisible(false);
		
		sizeField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					System.out.println(sizeField.getText().trim().matches("(\\d)*(\\.)?(\\d)*"));
					if(sizeField.getText().trim().isEmpty()||(!sizeField.getText().matches("(\\d)*(\\.)?(\\d)*"))){
						throw new IncorrectFieldValueException(sizeFail,sizeField);
					}else sizeFail.setVisible(false);
				} catch (IncorrectFieldValueException e1) {
					e1.setMessage("incorrect value");
				}
			}
		});
		
		nameField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					if(nameField.getText().trim().isEmpty()) throw new IncorrectFieldValueException(nameFail,nameField);
					else nameFail.setVisible(false);
				} catch (IncorrectFieldValueException e1) {
					e1.setMessage("can't be empty");
					e1.clear();
				}
			}
		});
		
		keyField.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent arg0) {
				try {
					if(keyField.getText().trim().isEmpty()) throw new IncorrectFieldValueException(keyFail,keyField);
					else keyFail.setVisible(false);
				} catch (IncorrectFieldValueException e1) {
					e1.setMessage("can't be empty");
					e1.clear();
				}
			}
		});
		
		BCreate.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					if (keyField.getText().trim().isEmpty()) throw new IncorrectFieldValueException(keyFail, keyField);
					if (nameField.getText().trim().isEmpty()) throw new IncorrectFieldValueException(nameFail, nameField);
					if (sizeFail.isVisible()|sizeField.getText().trim().isEmpty()) throw new IncorrectFieldValueException(sizeFail, sizeField);
					double square = Double.parseDouble(sizeField.getText());
					km.getKeep().addTerritory(keyField.getText(), new Territory(nameField.getText(),square));
					km.getKeep().territoryCreated(new TerritoryAddEvent(e));
					dialog.dispose();
				}catch(IncorrectFieldValueException ifv){
					ifv.setMessage("incorrect value");
					ifv.clear();
				}
			}
		});
		
		dialog.open();
	}
}
