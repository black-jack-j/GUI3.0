package bdata;

public class KPlace {
	
	private String name;
	private String square;
	private String key;
	private int ownerId;
	
	public int getOwnerId() {
		return ownerId;
	}
	
	public KPlace(int ownerId, String key, String name, String square){
		this.ownerId = ownerId;
		this.key = key;
		this.setName(name);
		this.setSquare(square);
	}
	
	public void setSquare(String square2) {this.square = square2;}

	public String getSquare() {return square;}
	
	public void setName(String name2) {this.name = name2;}

	public String getName() {return name;}
	
	public String getKey(){return key;}

}
