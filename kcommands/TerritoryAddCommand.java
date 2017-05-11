package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TerritoryAddCommand extends BDKCommand {
	
	private String id;
	private int mapId;
	private String name;
	private double size;
	
	public TerritoryAddCommand(String key, int mapId, String name, String size, int priority) {
		super(priority);
		this.id = key;
		this.mapId =mapId;
		this.size = Double.parseDouble(size);
		this.name = name;
	}

	@Override
	public void execute() {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO TERRITORIES (ID, MAP_ID, NAME, SIZE) VALUES (?, ?, ?, ?);");
			statement.setString(1, id);
			statement.setInt(2, mapId);
			statement.setString(3, name);
			statement.setDouble(4, size);
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
