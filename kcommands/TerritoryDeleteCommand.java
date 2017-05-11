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
			PreparedStatement statement = connection.prepareStatement("DELETE FROM TERRITORIES WHERE ID LIKE ? AND MAP_ID = ? ;");
			statement.setString(1, key);
			statement.setInt(2, mapId);
			statement.execute();
			OpenTerritoriesCommand otc = new OpenTerritoriesCommand(mapId, 0);
			otc.setHandler(handler);
			otc.setConnection(connection);
			otc.execute();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
