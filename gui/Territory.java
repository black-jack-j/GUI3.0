package gui;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class Territory {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	private int id;
	
	private String name;
	private double square;
	
	@ManyToOne
	private Keeper iMap;
	
	public Keeper getMap() {
		return iMap;
	}
	public void setMap(Keeper map) {
		this.iMap = map;
	}
	public Territory(){
		
	}
	public Territory(String name, double square){
		this.setName(name);
		this.setSquare(square);
	}
	public void setSquare(double square2) {this.square = square2;}
	
	public void setName(String name2) {this.name = name2;}

	public void expand(double psnts) {square = square + square * psnts / 100;}

	public void decrease(double psnts) {square = square - square * psnts / 100;}

	public double getSquare() {return square;}

	public String getName() {return name;}
	
	public int compareTo(Territory measure){return (new Double (this.getSquare())).compareTo(measure.getSquare());}

	public boolean equals(Territory tmp){
		return (this.name.equals(tmp.getName()) && (this.square==tmp.getSquare()));
	}
}
