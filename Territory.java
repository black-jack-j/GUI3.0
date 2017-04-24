
public class Territory {
	private String name;
	private double square;
	public Territory(String name, double square){
		this.setName(name);
		this.setSquare(square);
	}
	private void setSquare(double square2) {this.square = square2;}
	
	private void setName(String name2) {this.name = name2;}

	public void expand(double psnts) {square = square + square * psnts / 100;}

	public void decrease(double psnts) {square = square - square * psnts / 100;}

	public double getSquare() {return square;}

	public String getName() {return name;}
	
	public int compareTo(Territory measure){return (new Double (this.getSquare())).compareTo(measure.getSquare());}

	public boolean equals(Territory tmp){
		return (this.name.equals(tmp.getName()) && (this.square==tmp.getSquare()));
	}
}
