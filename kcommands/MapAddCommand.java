package kcommands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bdata.KMap;

public class MapAddCommand extends BDKCommand {
	
	private int ownerId;
	private String name;
	
	public MapAddCommand(int ownerId, String name, int priority) {
		super(priority);
		this.name = name;
		this.ownerId = ownerId;
	}

	@Override
	public void execute() {
		try {
			PreparedStatement statement = connection.prepareStatement("INSERT INTO MAPS (OWNER_ID, NAME, SIZE) VALUES(?, ?, 0);");
			statement.setInt(1, ownerId);
			statement.setString(2, name);
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
