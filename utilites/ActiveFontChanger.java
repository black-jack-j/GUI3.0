package utilites;

import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;

public class ActiveFontChanger {
	public static <T extends Scrollable> FontData getPrettyFont(T parent, int persentage){
		FontData fd = new FontData();
		FormData formD = (FormData) parent.getLayoutData();
		int height = (formD.bottom.numerator-formD.top.numerator)*parent.getClientArea().height*persentage/100;
		fd.setHeight(height);
		return fd;
	}
}
