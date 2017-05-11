package bdata;

public class KMap {
	
	private int id;
	
	private String name;
	
	private String size;
	
	public KMap(String name, int id){
		this.setName(name);
		this.id = id;
	}
	
	public KMap(String name, int id, String size){
		size = String.valueOf(size);
		this.setName(name);
		this.id = id;
	}
	
	public void setName(String name){this.name = name;}
	
	public String getName () {return this.name;}
	
	public String getSize(){
		return size;
	}
	
	public void setSize(String size){
		this.size = size;
	}
	
	public int getId() {
		return id;
	}
}
