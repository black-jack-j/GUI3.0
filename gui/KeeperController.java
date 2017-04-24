package gui;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

public class KeeperController {
	Set<Note> collections;
	private Path workPath;
	private List<KeeperListener> keeperListeners;
	public KeeperController(){
		collections = new TreeSet<>();
		keeperListeners = new ArrayList<>();
		workPath = Paths.get("C:/users/fitisovdmtr/lab/config.xml");
		SAXBuilder build = new SAXBuilder();
		Document doc;
		try {
			File f = new File(workPath.toString());
			f.setReadable(true);
			doc = build.build(f);
			Element root = doc.getRootElement();
			List<Element> children = root.getChildren("path");
			for(int i = 0; i<children.size();i++){
				Note n = load(children.get(i).getAttributeValue("value"));
				collections.add(n);
			}
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	void printNotes(){
		collections.forEach((Note n)->{
			System.out.println(n.getPath()+" - " + n.getKeep().getName() + ": "+ n.getKeep().getSize());
		});
	}
	void loadKeeper(String p){
		Path path = Paths.get(p);
		File f = new File(p);
		f.setReadable(true);
		try {
			KeepModel km = new KeepModel(XMLParser.getStorage(f));
			collections.add(new Note(path,km));
		} catch (JDOMException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private Note load(String p){
		Note keep = null;
		try {
			Note tmp;
			File f = new File(p);
			f.setReadable(true);
			Keeper k = XMLParser.getStorage(f);
			tmp = new Note(Paths.get(p),new KeepModel(k));
			tmp.getKeep().addTerritoryListener(new TerritoryListener(){

				@Override
				public void territoryCreated(TerritoryAddEvent tae) {
					keeperUpdated(new KeeperRefreshEvent(tae));
				}

				@Override
				public void territoryRemoved(TerritoryRemoveEvent tre) {
					switch(tre.m){
					case remove:{
						tmp.getKeep().removeTerritory(tre.getKey());
						break;
					}
					case removeLower:{
						tmp.getKeep().removeLower(tre.getKey());
					}
					default:break;
					}
					//keeperReaded(new KeeperSelectionEvent(tre,view.indexOf(keep)));
				}
				
			});
			keep = tmp;
		} catch (JDOMException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return keep;
	}
	void addKeeperListener(KeeperListener kl){
		this.keeperListeners.add(kl);
	}
	
	void removeKeeperListener(KeeperListener kl){
		this.keeperListeners.remove(kl);
	}
	
	void keeperCreated(KeeperAddEvent addEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperCreated(addEv);
		});
	}
	
	void keeperReaded(KeeperSelectionEvent selEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperReaded(selEv);
		});
	}
	
	void keeperUpdated(KeeperRefreshEvent refEv){
		keeperListeners.forEach((KeeperListener kl)->{
			kl.keeperUpdated(refEv);
		});
	}
	
	void keeperDeleted(KeeperRemoveEvent remEv){
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
	void removeNote(Note n){
		collections.remove(n);
	}
	void close(){
		collections.forEach((Note n)->n.save());
		File f = new File(workPath.toString());
		Element rootElement = new Element("files");
		collections.forEach((Note p)->{
			Element el = new Element("path");
			el.setAttribute("value", p.getPath().toString());
			rootElement.addContent(el);
		});
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
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	void addKeeper(String name){
		collections.add(new Note(Paths.get(name), new KeepModel(new Keeper(name))));
	}
	List<List<String>> getStore(){
		List<List<String>> outer = new ArrayList<>();
		collections.forEach((Note n)->{
			List<String> inner = new ArrayList<>();
			inner.add(n.getKeep().getName());
			inner.add(n.getKeep().getSize());
			outer.add(inner);
		});
		return outer;
	}
	public Keeper getKeeper(int index){
		if (index < collections.size()) return getElement(index).getKeep().getKeeper();
		else return null;
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
}