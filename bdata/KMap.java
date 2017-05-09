package bdata;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.OneToMany;

@Entity
public class KMap {

	@Id
	@GeneratedValue(strategy = GenerationType.TABLE)
	private int id;
	
	private String name;
	
	private String size;
	
	@OneToMany(mappedBy="iMap", cascade=CascadeType.ALL)
	@MapKeyColumn(name="PLACES")
	private Map<String, KPlace> Places;
	
	public KMap(){
		
	}
	
	public KMap(String name){
		this.setName(name);
		Places = new HashMap<String, KPlace>();
	}
	
	public void addComponent(String key, KPlace a){
		Places.put(key, a);
		a.setMap(this);
	}
	
	public void setName(String name){this.name = name;}
	
	public String getName () {return this.name;}
	
	public Map<String, KPlace> getCollection(){return Places;}
	
	public String getSize(){
		return size;
	}
	
	public int getId() {
		return id;
	}
}
