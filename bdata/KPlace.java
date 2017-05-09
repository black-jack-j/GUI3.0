package bdata;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;


@Entity
public class KPlace {
	
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	private String name;
	private String square;
	
	@ManyToOne
	private KMap iMap;
	
	public KMap getMap() {
		return iMap;
	}
	
	public void setMap(KMap map) {
		this.iMap = map;
	}
	
	public KPlace(){
		
	}
	
	public KPlace(String name, String square){
		this.setName(name);
		this.setSquare(square);
	}
	
	public void setSquare(String square2) {this.square = square2;}

	public String getSquare() {return square;}
	
	public void setName(String name2) {this.name = name2;}

	public String getName() {return name;}

}
