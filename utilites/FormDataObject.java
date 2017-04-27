package utilites;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Scrollable;

public class FormDataObject<T> {
	public static <T extends Control, C extends Scrollable> T getFormedControl(int left, int top, int right, int bottom, Class<T> object, C parent, int style){
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
	
	public static <T extends Control, C extends Scrollable> T getFormedControl(FormAttachment left, int top, int right, int bottom, Class<T> object, C parent, int style){
		return getFormedControl(left, new FormAttachment(top), new FormAttachment(right), new FormAttachment(bottom), object, parent, style);
	}
	
	public static <T extends Control, C extends Scrollable> T getFormedControl(int left, FormAttachment top, int right, int bottom, Class<T> object, C parent, int style){
		return getFormedControl(new FormAttachment(left),top , new FormAttachment(right), new FormAttachment(bottom), object, parent, style);
	}
	
	public static <T extends Control, C extends Scrollable> T getFormedControl(int left, int top, FormAttachment right, int bottom, Class<T> object, C parent, int style){
		return getFormedControl(new FormAttachment(left),new FormAttachment(top), right, new FormAttachment(bottom), object, parent, style);
	}
	
	public static <T extends Control, C extends Scrollable> T getFormedControl(int left, int top, int right, FormAttachment bottom, Class<T> object, C parent, int style){
		return getFormedControl(new FormAttachment(left),new FormAttachment(top), new FormAttachment(right), bottom, object, parent, style);
	}
	
	public static <T extends Control, C extends Scrollable> T getFormedControl(FormAttachment left, FormAttachment top, 
											FormAttachment right, FormAttachment button, Class<T> object, C parent, int style){
		try {
			Constructor<T> constr = object.getConstructor(Composite.class, int.class);
			T control = constr.newInstance(parent, style);
			FormData fd = new FormData();
			fd.top = top;
			fd.left = left;
			fd.right = right;
			fd.bottom = button;
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
	
	public static <T extends Scrollable> T getFormedComposite(int left, int top, int right, int bottom, Class<T> object, Scrollable parent, int style){
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
	
	public static <T extends Scrollable> T getFormedComposite(FormAttachment left, int top, int right, int bottom, Class<T> object, T parent, int style){
		return getFormedComposite(left, new FormAttachment(top), new FormAttachment(right), new FormAttachment(bottom), object, parent, style);
	}
	
	public static <T extends Scrollable> T getFormedComposite(int left, FormAttachment top, int right, int bottom, Class<T> object, T parent, int style){
		return getFormedComposite(new FormAttachment(left),top , new FormAttachment(right), new FormAttachment(bottom), object, parent, style);
	}
	
	public static <T extends Scrollable> T getFormedComposite(int left, int top, FormAttachment right, int bottom, Class<T> object, T parent, int style){
		return getFormedComposite(new FormAttachment(left),new FormAttachment(top), right, new FormAttachment(bottom), object, parent, style);
	}
	
	public static <T extends Scrollable> T getFormedComposite(int left, int top, int right, FormAttachment bottom, Class<T> object, T parent, int style){
		return getFormedComposite(new FormAttachment(left),new FormAttachment(top), new FormAttachment(right), bottom, object, parent, style);
	}
	
	public static <T extends Scrollable> T getFormedComposite(FormAttachment left, FormAttachment top, 
											FormAttachment right, FormAttachment button, Class<T> object, T parent, int style){
		try {
			Constructor<T> constr = object.getConstructor(Composite.class, int.class);
			T control = constr.newInstance(parent, style);
			FormData fd = new FormData();
			fd.top = top;
			fd.left = left;
			fd.right = right;
			fd.bottom = button;
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
}
