package gui;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class IncorrectFieldValueException extends Exception{
	private Label label;
	private Text text;
	IncorrectFieldValueException(Label l, Text t){
		this.label = l;
		this.text = t;
	}
	public void setMessage(String s){
		label.setText(s);
		label.setVisible(true);
	}
	public void clear(){
		text.setText("");
	}
}
