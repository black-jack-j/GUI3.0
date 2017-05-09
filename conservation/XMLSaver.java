package conservation;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import gui.Keeper;
import gui.KeeperController;
import gui.Territory;

public class XMLSaver implements Watchdog{
	private BlockingQueue<File> que;
	private File file;
	
	public void setFile(File file) {
		this.file = file;
	}
	
	private KeeperController keeperController;
	Document doc;
	
	public XMLSaver(KeeperController kc){
		que = new ArrayBlockingQueue<File>(1);
		this.keeperController = kc;
		this.file = new File(keeperController.getPath());
	}
	private void getXMLForm(Keeper keeper){
		if (keeper == null) return;
		try {
			que.put(file);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		FileWriter osw = null;
		boolean isWritable = false;
		try {
			file.createNewFile();
			isWritable = file.canWrite();
			file.setWritable(true);
			osw = new FileWriter(file);
		} catch (FileNotFoundException e1) {
			System.out.println("File wasn't found");
			System.out.println(e1.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		Element rootElem = new Element("storage");
		rootElem.setAttribute("name", keeper.getName());
		rootElem.setAttribute("type", Keeper.class.getSimpleName());
		doc = new Document();
		keeper.getCollection().forEach((String key, Territory t)->{
			Element object = new Element("place");
			object.setAttribute("key", key);
			object.setAttribute("name", t.getName());
			object.setAttribute("square", String.valueOf(t.getSquare()));
			rootElem.addContent(object);
		});
		doc.setRootElement(rootElem);
		Format frmt = Format.getPrettyFormat();
		frmt.setEncoding("cp1251");
		XMLOutputter xml = new XMLOutputter(frmt);
		try {
			xml.output(doc, osw);
			osw.close();
		} catch (IOException e) {
			System.out.println("Bad");
		}
		file.setWritable(isWritable);
		que.clear();
	}
	
	public void  save(Keeper k) {
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				getXMLForm(k);
			}
			
		});
		t.start();
	}
	public Keeper load(){
		try {
			que.put(file);
		} catch (InterruptedException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		boolean isReadable = true;
		try {
			file.createNewFile();
			isReadable = file.canRead();
			file.setReadable(true);
		} catch (FileNotFoundException e1) {
			System.out.println("File wasn't found");
			System.out.println(e1.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		}
		SAXBuilder sab = new SAXBuilder();
		Keeper tmp = null;
		try{
			Document doc = sab.build(file);
			Element rootElem = doc.getRootElement();
			tmp = new Keeper(rootElem.getAttributeValue("name"));
			List<Element> collectionElements = rootElem.getChildren("place");
			for (Element el:collectionElements){
				Territory place = new Territory(el.getAttributeValue("name"), Double.parseDouble(el.getAttributeValue("square")));
				tmp.addComponent(el.getAttributeValue("key"), place);
			}
		}catch(Exception e){
			e.printStackTrace();
			tmp = new Keeper("Default");
		}
		file.setReadable(isReadable);
		que.clear();
		return tmp;
	}
}
