package functions;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.jdom2.JDOMException;

import gui.KeepModel;
import gui.Keeper;
import gui.Note;
import gui.XMLParser;
import tevents.*;

public class KeeperLoader implements Runnable{
	
	private boolean alright;
	private Note note;
	private Path path;
	private List<TerritoryListener> listeners;
	
	public KeeperLoader(Path p, TerritoryListener ... ltnrs){
		path = p;
		alright = true;
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
				System.out.println(note==null);
				System.out.println(k.getName() + + k.getSize());
			}else{
				note = new Note(path, new KeepModel(new Keeper("Default")));
			}
			note.getKeep().AddTerritoryListener(listeners);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			alright = false;
		} catch (JDOMException e) {
			alright = false;
		}
	}
	
	public Note getNote(){
		return note;
	}
	
	public boolean getStatus(){
		return alright;
	}

}
