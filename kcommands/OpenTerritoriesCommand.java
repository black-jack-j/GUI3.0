package kcommands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bdata.KPlace;

public class OpenTerritoriesCommand extends BDKCommand {
    private int map_id;

    public OpenTerritoriesCommand(int map_id, int priority) {
        super(priority);
        this.map_id = map_id;
    }
//
    @Override
    public void execute() {
        try {
        	PreparedStatement statement = connection.prepareStatement("SELECT * FROM territories WHERE map_id = ?;");
        	statement.setInt(1, map_id);
        	ResultSet result = statement.executeQuery();
        	List<KPlace> arr = new ArrayList<>();
      		while(result.next()){
      			KPlace tmp = new KPlace(result.getInt(2), result.getString(1), result.getString(3), result.getString(4));
      			arr.add(tmp);
      		}
      		KPlace[] array = new KPlace[arr.size()];
      		array = arr.toArray(array);
      		ClientTerritoryUpdate upd = new ClientTerritoryUpdate(array, 0);
      		handler.notifyListeners(upd);
        	} catch (SQLException e) {
            System.out.println("Problem while reading from database");
        }
    }
}
