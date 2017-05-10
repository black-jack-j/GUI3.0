package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TerritoryUpdateSizeCommand extends BDKCommand {
    int key;
    String size;

    public TerritoryUpdateSizeCommand(int key, String size, int priority) {
        super(priority);
        this.key = key;
        this.size = size;
    }

    @Override
    public void execute() {
        try {
        PreparedStatement statement = connection.prepareStatement("UPDATE TERRITORIES SET size = ? WHERE id = ?;");
        statement.setString(1, size);
        statement.setInt(2, key);
        statement.execute();
        //new command
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при попытке изменения размера территории.");
        }
    }
}
