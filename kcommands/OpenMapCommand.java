package kcommands;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bdata.KMap;

public class OpenMapCommand extends BDKCommand {
    int owner_id;

    public OpenMapCommand(int owner_id, int priority) {
        super(priority);
        this.owner_id = owner_id;
    }

    @Override
    public void execute() {
        try {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM maps WHERE owner_id = ?;");
        statement.setInt(1, owner_id);
        ResultSet result = statement.executeQuery();
        List<KMap> arr = new ArrayList<>();
		while(result.next()){
			KMap tmp = new KMap(result.getString(3), result.getInt(1), String.valueOf(result.getInt(4)));
			arr.add(tmp);
		}
		KMap[] array = new KMap[arr.size()];
		array = arr.toArray(array);
		ClientMapUpdate upd = new ClientMapUpdate(array,0);
        handler.notifyListeners(upd);
        } catch (SQLException e) {
            System.out.println("Problem while reading from database");
        }
    }
}
