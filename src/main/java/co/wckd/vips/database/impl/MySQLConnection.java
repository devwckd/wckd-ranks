package co.wckd.vips.database.impl;

import co.wckd.vips.database.DatabaseConnection;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@RequiredArgsConstructor
public class MySQLConnection implements DatabaseConnection {

    private final String hostname;
    private final String username;
    private final String password;
    private final String schema;

    private Connection connection;

    @Override
    public void openConnection() {
        try {

            Class.forName("com.mysql.jdbc.Driver");
            connect();
            createTables();

        } catch (Exception e) {
            // TODO: log
        }
    }

    @Override
    public Connection getConnection(boolean autoCommit) {
        try {

            if (connection == null || connection.isClosed())
                connect();

            connection.setAutoCommit(autoCommit);

            return connection;

        } catch (Exception exception) {
            // TODO: log
            return null;
        }
    }

    private void connect() throws SQLException {
        connection = DriverManager.getConnection("jdbc:mysql://" + hostname + "/" + schema, username, password);
    }

    private void createTables() {
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS vips (" +
                    "uuid VARCHAR(36) NOT NULL, " +
                    "vip_type VARCHAR(50) NOT NULL, " +
                    "time BIGINT(19) NOT NULL," +
                    "is_active BOOLEAN NOT NULL " +
                    "primary key (uuid, vip_type) " +
                    ")");
        } catch (Exception exception) {
            // TODO: log
        }
    }

}
