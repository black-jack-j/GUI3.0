package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import functions.KeeperLoader;
import functions.KeeperSaver;
import kevents.*;
import tevents.*;

public class KeeperController {
	Set<Note> collections;
	private Path workPath;
	private Path defaultPath;
	private List<KeeperListener> keeperListeners;
	public KeeperController(String path) throws IOException{
		init(path);
	}
	public KeeperController(String path, TerritoryListener ...listeners) throws IOException{
		init(path, listeners);
	}
	void printNotes(){
		collections.forEach((Note n)->{
			System.out.println(n.getPath()+" - " + n.getKeep().getName() + ": "+ n.getKeep().getSize());
		});
	}
	void loadKeeper(Set<Note> s, String p, TerritoryListener ...listeners){
			KeeperLoader kL = new KeeperLoader(collections, Paths.get(p),listeners);
			Thread t = new Thread(kL);
			t.setPriority(Thread.MAX_PRIORITY);
			t.start();
	}
	public void loadKeeper(String p, TerritoryListener ...listeners){
		KeeperLoader kL = new KeeperLoader(collections, Paths.get(p),listeners);
		Thread t = new Thread(kL);
		t.setPriority(Thread.MAX_PRIORITY);
		t.start();
	}
	public void addKeeperListener(KeeperListener kl){
		this.keeperListeners.add(kl);
	}
	
	void removeKeeperListener(KeeperListener kl){
		this.keeperListeners.remove(kl);
	}
	
	public void keeperCreated(KeeperAddEvent addEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperCreated(addEv);
		});
	}
	
	public void keeperReaded(KeeperSelectionEvent selEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperReaded(selEv);
		});
	}
	
	public void keeperUpdated(KeeperRefreshEvent refEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperUpdated(refEv);
		});
	}
	
	public void keeperDeleted(KeeperRemoveEvent remEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperDeleted(remEv);
		});
	}
	
	int getIndex(Note n){
		if (collections.contains(n)){
			Note[] notes = new Note[collections.size()];
			notes = collections.toArray(notes);
			for(int i=0;i<collections.size();i++){
				if (notes[i].equals(n)) return i;
			}
		}return -1;
	}
	public Note getElement(int index){
		Note[] notes = new Note[collections.size()];
		notes = collections.toArray(notes);
		if (index < collections.size()) return notes[index];
		return null;
	}
	void removeNote(int i){
		if(i<collections.size()){
			Note n = getElement(i);
			collections.remove(n);
		}
	}
	public void removeNote(Note n){
		collections.remove(n);
	}
	public void close(){
		collections.forEach((Note n)->{
			KeeperSaver ks = new KeeperSaver(n);
			Thread t = new Thread(ks);
			t.start();
		});
		Thread t = new Thread(new Runnable(){

			@Override
			public void run() {
				File f = new File(workPath.toString());
				Element rootElement = new Element("files");
				synchronized(collections){
					collections.forEach((Note p)->{
						Element el = new Element("path");
						el.setAttribute("value", p.getPath().toString());
						rootElement.addContent(el);
					});
				}
				synchronized(f){
					try {
						f.createNewFile();
						f.setWritable(true);
						FileWriter fw = new FileWriter(f);
						Document doc = new Document();
						doc.setRootElement(rootElement);
						Format frmt = Format.getPrettyFormat();
						frmt.setEncoding("cp1251");
						XMLOutputter xml = new XMLOutputter();
						xml.output(doc, fw);
						System.out.println("All clear");
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
			
		});
		t.start();
	}
	public void addKeeper(String name){
		collections.add(new Note(Paths.get(defaultPath.toString()+name+".xml"), new KeepModel(new Keeper(name))));
	}
	List<Note> getNStorage(){
		List<Note> l = new ArrayList<Note>();
		collections.forEach((Note n)->l.add(n));
		return l;
	}
	public void markPath(String s, Note n){
		n.setPath(Paths.get(s));
	}
	public String showPath(Note n){
		return n.getPath().toString();
	}
	
	private void init(String path, TerritoryListener ...listeners) throws IOException {
		collections = new TreeSet<>();
		keeperListeners = new ArrayList<>();
		workPath = Paths.get(path);
		defaultPath = Paths.get("C:/users/fitisovdmtr/lab/collections/");
		File f = new File(workPath.toString());
		new File(workPath.getParent().toString()).mkdirs();
		try {
			if(!f.createNewFile()){
				SAXBuilder build = new SAXBuilder();
				Document doc;
				f.setReadable(true);
				doc = build.build(f);
				Element root = doc.getRootElement();
				List<Element> children = root.getChildren("path");
				for(int i = 0; i<children.size();i++){
					loadKeeper(collections, children.get(i).getAttributeValue("value"),listeners);
				}
			}else{
				String s = "<?xml version=\"1.0\"?/>";
				FileWriter fw = new FileWriter(f);
				fw.write(s);
				fw.close();
			}
		} catch (JDOMException e) {
			String s = "<?xml version=\"1.0\"?/>";
			FileWriter fw = new FileWriter(f);
			fw.write(s);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}