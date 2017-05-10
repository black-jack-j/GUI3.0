package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TerritoryDeleteCommand extends BDKCommand {
	String key;
	int mapId;
	
	public TerritoryDeleteCommand(int mapId, String key, int priority) {
		super(priority);
		this.mapId = mapId;
		this.key = key;
	}

	@Override
	public void execute() {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM TERRITORIES WHERE ID LIKE ? AND MAP_ID = ?");
			statement.setString(1, key);
			statement.setInt(2, mapId);
			statement.execute();
			//new command 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
