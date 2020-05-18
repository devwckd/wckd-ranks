package co.wckd.ranks.database;

import java.sql.Connection;

public interface DatabaseConnection {

    void openConnection();

    Connection getConnection(boolean autoCommit);

}
