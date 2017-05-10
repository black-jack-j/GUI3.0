package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpenTerritoriesCommand extends BDKCommand {
    String map_id;

    public OpenTerritoriesCommand(String map_id, int priority) {
        super(priority);
        this.map_id = map_id;
    }
//
    @Override
    public void execute() {
        try {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM territories WHERE map_id = ?;");
        statement.setString(1, map_id);
        statement.execute();
        //new command
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при попытке считывании таблицы территорий");
        }
    }
}
