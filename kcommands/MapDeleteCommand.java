package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MapDeleteCommand extends BDKCommand {

	private int id;
	
	public MapDeleteCommand(int id, int priority) {
		super(priority);
		this.id = id;
	}
	
	@Override
	public void execute() {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM MAPS WHERE ID = ?;");
			statement.setInt(1, id);
			statement.execute();
			//new command 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
