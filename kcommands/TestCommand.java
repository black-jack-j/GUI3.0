package kcommands;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class TestCommand extends BDKCommand {
	
	private int ownerId;
	
	public TestCommand(int ownerId, int priority) {
		super(priority);
		this.ownerId = ownerId;
	}

	@Override
	public void execute() {
		try {
			Statement statement = connection.createStatement();
			ResultSet result = statement.executeQuery("SELECT * FROM USERS;");
			while(result.next()) System.out.println(result.getString(2));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
