package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class MapDeleteCommand extends BDKCommand {

	private int id;
	private int ownerId;
	public MapDeleteCommand(int ownerId, int id, int priority) {
		super(priority);
		this.id = id;
		this.ownerId = ownerId;
	}
	
	@Override
	public void execute() {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM MAPS WHERE ID = ?;");
			statement.setInt(1, id);
			statement.execute();
			OpenMapCommand omc = new OpenMapCommand(ownerId, 0);
			omc.setHandler(handler);
			omc.setConnection(connection);
			omc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
