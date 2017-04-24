package gui;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;


public class Keeper{
	private String name;
	
	private Map<String, Territory> Places;
	
	public Keeper(String name, Territory ... p){
		this.setName(name);
		Places = new HashMap<>();
		for (int i=0;i<p.length;i++) Places.put( p[i].getName(), p[i]);
	}
	public Keeper(String name, String[] key, Territory[] p){
		this.setName(name);
		Places = new HashMap<>();
		for (int i=0;i<key.length;i++) Places.put(key[i], p[i]);
	}
	public Keeper(String name, int size){
		this.setName(name);
		Places = new HashMap<>();
	}
	public void addComponent(Territory m) {Places.put(m.getName(), m);}
	
	 public void addComponent(String key, Territory a){Places.put(key, a);}
	 /**
	  * Inserts element to Collecion 
	 * @author fitisovdmtr
	 * @param name
	 * @return
	 */

	public Territory getComponent(String name) {return Places.get(name);}
	
	public void setName(String name){this.name = name;}
	
	public String getName () {return this.name;}
	
	private String printElement(Territory p){return new String("Place's name: "+p.getName()+", Place's square: "+p.getSquare());}
	
	/**
	 *Deleting elements from Collection
	 *@author fitisovdmtr 
	 */
	public void clear(){Places.clear();}
	
	public Map<String, Territory> getCollection(){return Places;}
	
	public String getIngo(){
		String reply = "";
		return reply;
	}
	
	public boolean removeComponent(String key) {
		return !(Places.remove(key)==null);
	}
	public int getSize(){
		return this.Places.size();
	}
	public void showFormat(){
		Places.forEach((String key, Territory t)->System.out.println(key+" " + this.printElement(t)));
	}
	/**
	 * removes all element which squares are lower than measure.square
	 * @author fitisovdmtr
	 * @param measure
	 */
	public void removeLower(Territory measure){
		for (Iterator<Map.Entry<String, Territory>> iter = Places.entrySet().iterator();iter.hasNext();){
			Map.Entry<String, Territory> entry = iter.next();
			if (entry.getValue().compareTo(measure) < 0) {
				iter.remove();
			}
		}
	}
	/**
	 * 
	 */
	public void printStorage(){
		System.out.println("Collection size: "+Places.size()+"; Collection type: "+ Places.getClass()+"; Elements: ");
		Places.forEach((String s, Territory t)->System.out.printf("Place's name: %s; Place's square: %f \r\n", t.getName(), t.getSquare()));
	}
	
	public void getXML(File f){
		XMLParser xmlp = new XMLParser();
		xmlp.getXMLForm(f);
	}
	public boolean equals(Keeper tmp){
		if(this.getCollection().size()!=tmp.getCollection().size()) return false;
		Iterator<Map.Entry<String, Territory>> iter2 = tmp.getCollection().entrySet().iterator();
		for (Iterator<Map.Entry<String, Territory>> iter = this.getCollection().entrySet().iterator();iter.hasNext();){
			Map.Entry<String, Territory> entry = iter.next();
			Map.Entry<String, Territory> entry2 = iter2.next();
			if (!(entry.getValue().equals(entry2.getValue()))) return false;
		}
		return true;
	}
	private class XMLParser{
		Document doc;
		public void getXMLForm(File file){
			OutputStreamWriter osw = null;
			try {
				osw = new OutputStreamWriter(new FileOutputStream(file));
			} catch (FileNotFoundException e1) {
				System.out.println("File wasn't found");
				System.out.println(e1.getMessage());
			}
			Element rootElem = new Element("storage");
			rootElem.setAttribute("name", name);
			rootElem.setAttribute("type", Places.getClass().getSimpleName());
			doc = new Document();
			for(Iterator<Map.Entry<String,Territory>> iter = Places.entrySet().iterator(); iter.hasNext();){
				Map.Entry<String,Territory> entry = iter.next();
				Field[] fields = entry.getValue().getClass().getDeclaredFields();
				Element collectionElement = new Element("object");
				collectionElement.setAttribute("key", entry.getKey());
				Element tmpElement;
				for (Field f : fields){
					tmpElement = StorageReplica(f, entry.getValue());
					collectionElement.addContent(tmpElement);
				}
				rootElem.addContent(collectionElement);
			}
			doc.setRootElement(rootElem);
			Format f = Format.getPrettyFormat();
			f.setEncoding("cp1251");
			XMLOutputter xml = new XMLOutputter(f);
			try {
				xml.output(doc, osw);
			} catch (IOException e) {
				System.out.println("Bad");
			}
		}
		private Element StorageReplica(Field f, Object obj){
			f.setAccessible(true);
			Element elem = new Element(f.getName());
			String type = f.getType().getSimpleName();
			elem.setAttribute("type", type);
			if (f.getType().isPrimitive()){
					try {
						if (type.equals("int")) elem.addContent(f.getInt(obj)+"");
						else if (type.equals("long")) elem.addContent(f.getLong(obj)+"");
						else if (type.equals("byte")) elem.addContent(f.getByte(obj)+"");
						else if (type.equals("char")) elem.addContent(f.getChar(obj)+"");
						else if (type.equals("boolean")) elem.addContent(f.getBoolean(obj)+"");
						else if (type.equals("float")) elem.addContent(f.getFloat(obj)+"");
						else if (type.equals("double")) elem.addContent(f.getDouble(obj)+"");
						else if (type.equals("short")) elem.addContent(f.getShort(obj)+"");
					} catch (IllegalArgumentException e) {
						System.out.println(e.getClass().getName());
					} catch (IllegalAccessException e) {
						System.out.println(e.getClass().getName());
					}
			}
			else if (f.getType().getSimpleName().equals("String")){
				try {
					elem.addContent((String)f.get(obj));
				} catch (IllegalArgumentException e) {
					System.out.println(e.getClass().getName());
				} catch (IllegalAccessException e) {
					System.out.println(e.getClass().getName());
				}
			} else
				try {
					if (f.get(obj) instanceof List){
						Object[] fields = ((List)(f.get(obj))).toArray();
						for (Object o: fields){
							Field[] flds = o.getClass().getDeclaredFields();
							Element tmpElem = new Element("object");
							for (Field fld: flds){
								tmpElem.addContent(StorageReplica(fld, o));
							}
							elem.addContent(tmpElem);
						}
					}
					else if (f.get(obj) instanceof Map){
						
					}
				} catch (IllegalArgumentException e) {
					System.out.println(e.getClass().getName());
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					System.out.println(e.getClass().getName());
				}
			return elem;
		}
	}
	public void save(File f) {
		// TODO Auto-generated method stub
		this.getXML(f);
	}
}
