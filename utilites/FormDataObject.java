package utilites;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

public class FormDataObject<T> {
	public static <T extends Control> T getFormedControl(int left, int top, int right, int bottom, Class<T> object, Composite parent, int style){
		try {
			Constructor<T> constr = object.getConstructor(Composite.class, int.class);
			T control = constr.newInstance(parent, style);
			FormData fd = new FormData();
			fd.top = new FormAttachment(top);
			fd.left = new FormAttachment(left);
			fd.right = new FormAttachment(right);
			fd.bottom = new FormAttachment(bottom);
			control.setLayoutData(fd);
			return control;
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	public static Composite getFormedComposite(int left, int top, int right, int bottom, Composite parent, int style){
		Composite comp = new Composite(parent, style);
		FormData fd = new FormData();
		fd.top = new FormAttachment(top);
		fd.left = new FormAttachment(left);
		fd.right = new FormAttachment(right);
		fd.bottom = new FormAttachment(bottom);
		comp.setLayoutData(fd);
		return comp;
	}
}
