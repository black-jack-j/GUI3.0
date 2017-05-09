package bdata;

public class KMap {
	
	private int id;
	
	private String name;
	
	private String size;
	
	public KMap(){
		
	}
	
	public KMap(String name){
		this.setName(name);
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
