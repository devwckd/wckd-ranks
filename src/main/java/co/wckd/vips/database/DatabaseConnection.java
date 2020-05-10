package co.wckd.vips.database;

import java.sql.Connection;

public interface DatabaseConnection {

    void openConnection();

    Connection getConnection(boolean autoCommit);

}
