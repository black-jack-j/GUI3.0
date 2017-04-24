package gui;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Comparator;

public class Note implements Comparable<Note>{
	private Path path;
	private KeepModel keep;
	public Note(Path p, KeepModel k){
		path = p;
		keep = k;
	}
	@Override
	public boolean equals(Object o){
		return path.equals(((Note)o).getPath());
	}
	public Path getPath(){
		return path;
	}
	public void setPath(Path p){
		this.path = p;
	}
	public KeepModel getKeep(){
		return keep;
	}
	@Override
	public int compareTo(Note arg0) {
		return keep.getKeeper().getSize()-arg0.getKeep().getKeeper().getSize();
	}
	public void save(){
		File f = new File(path.toString());
		try {
			f.createNewFile();
			f.setWritable(true);
			keep.getKeeper().save(f);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
