package functions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.jdom2.JDOMException;

import gui.KeepModel;
import gui.Keeper;
import gui.Note;
import gui.XMLParser;
import tevents.*;

public class KeeperLoader implements Runnable{
	
	private Collection<Note> commonRes;
	private Note note;
	private Path path;
	private List<TerritoryListener> listeners;
	
	public KeeperLoader(Set<Note> col, Path p, TerritoryListener ... ltnrs){
		commonRes = col;
		path = p;
		note = null;
		listeners = new ArrayList<>();
		for(TerritoryListener tl:ltnrs) listeners.add(tl);
	}
	
	@Override
	public void run() {
		File f = new File(path.toString());
		try {
			if (!f.createNewFile()){
				boolean b = f.canRead();
				f.setReadable(true);
				Keeper k = XMLParser.getStorage(f);
				f.setReadable(b);
				note = new Note(path, new KeepModel(k));
			}else{
				note = new Note(path, new KeepModel(new Keeper("Default")));
			}
			note.getKeep().AddTerritoryListener(listeners);
			synchronized(commonRes){
				commonRes.add(note);
			}
		} catch (IOException e) {
			System.out.println("io");
		} catch (JDOMException e) {
			System.out.println("jdom");
		}
	}
	
	public Note getNote(){
		return note;
	}

}
