package kcommands;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OpenMapCommand extends BDKCommand {
    String owner_id;

    public OpenMapCommand(String owner_id, int priority) {
        super(priority);
        this.owner_id = owner_id;
    }

    @Override
    public void execute() {
        try {
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM maps WHERE owner_id = ?;");
        statement.setString(1, owner_id);
        statement.execute();
        //new command
        } catch (SQLException e) {
            System.out.println("Возникла ошибка при попытке считывании таблицы maps");
        }
    }
}
