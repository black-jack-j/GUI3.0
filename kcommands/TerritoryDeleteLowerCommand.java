package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TerritoryDeleteLowerCommand extends BDKCommand {

	private int mapId;
	private int size;
	
	public TerritoryDeleteLowerCommand(int mapId, int size, int priority) {
		super(priority);
		this.mapId = mapId;
		this.size = size;
	}

	@Override
	public void execute() {
		try {
			PreparedStatement statement = connection.prepareStatement("DELETE FROM TERRITORIES WHERE MAP_ID = ? AND SIZE < ? ;");
			statement.setInt(1, mapId);
			statement.setInt(2, size);
			statement.execute();
			//new command 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
