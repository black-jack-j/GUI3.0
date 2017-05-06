package datab;
import java.sql.*;
public class Athena {
	public static void main(String[] args){
		try {
			Class.forName("org.postgresql.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			Connection db = DriverManager.getConnection("jdbc:postgresql:postgres",
							"postgres", "evening64night");
			printAll(db,"GHOST");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void printAll(Connection db, String name){
		Statement st;
		try {
			st = db.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM " + name + ";");
			while(rs.next()){
				System.out.println(rs.getString(2));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
