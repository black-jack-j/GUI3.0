package functions;
import java.util.Map.Entry;
import gui.Territory;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

public class KeeperSizeEditingSupport extends EditingSupport{
	private TableViewer viewer;
	private CellEditor cell;
		
	public KeeperSizeEditingSupport(TableViewer viewer) {
		super(viewer);
		this.viewer = viewer;
		cell = new TextCellEditor(viewer.getTable());
	}
	@Override
	protected boolean canEdit(Object arg0) {
	// TODO Auto-generated method stub
		return true;
	}
	@Override
	protected CellEditor getCellEditor(Object arg0) {
		// TODO Auto-generated method stub
		return cell;
	}
	@Override
	protected Object getValue(Object arg0) {
		// TODO Auto-generated method stub
		return String.valueOf(((Entry<String, Territory>)arg0).getValue().getSquare());
	}
	@Override
	protected void setValue(Object arg0, Object arg1) {
		if (String.valueOf(arg1).matches("(\\d)*([\\.,])?(\\d)*")){
			String s = String.valueOf(arg1).replace(',', '.');
			if (!s.trim().isEmpty())((Entry<String, Territory>)arg0).getValue().setSquare(Double.parseDouble(s));
		}
		viewer.update(arg0, null);
	}
}
