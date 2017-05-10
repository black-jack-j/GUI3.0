package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TerritoryUpdateNameCommand extends BDKCommand {
    int key;
    String name;

    public TerritoryUpdateNameCommand(int key, String name, int priority) {
        super(priority);
        this.key = key;
        this.name = name;
    }

    @Override
    public void execute() {
        try {
        PreparedStatement statement = connection.prepareStatement("UPDATE TERRITORIES SET name = ? WHERE ID = ?;");
        statement.setString(1, name);
        statement.setInt(2, key);
        statement.execute();
        //new command
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при попытке изменения имени территории.");
        }
    }
}
