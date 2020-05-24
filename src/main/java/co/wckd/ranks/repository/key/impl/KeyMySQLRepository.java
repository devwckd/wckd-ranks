package co.wckd.ranks.repository.key.impl;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.database.DatabaseConnection;
import co.wckd.ranks.entity.key.Key;
import co.wckd.ranks.repository.key.KeyRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class KeyMySQLRepository implements KeyRepository {

    private static final String FIND_STATEMENT = "SELECT * FROM keys WHERE key = ?;";
    private static final String INSERT_STATEMENT = "REPLACE INTO keys VALUES (?, ?, ?);";
    private static final String DELETE_STATEMENT = "DELETE FROM keys WHERE key = ?;";

    private final RanksPlugin plugin;
    private final Adapter adapter;
    private final DatabaseConnection databaseConnection;

    public KeyMySQLRepository(RanksPlugin plugin, DatabaseConnection databaseConnection) {
        this.plugin = plugin;
        this.adapter = plugin.getAdapter();
        this.databaseConnection = databaseConnection;
    }

    @Override
    public Key find(String keyString) {
        try (PreparedStatement statement = databaseConnection.getConnection(true).prepareStatement(FIND_STATEMENT)) {

            statement.setString(1, keyString);
            ResultSet resultSet = statement.executeQuery();
            return adapter.adapt(resultSet, ResultSet.class, Key.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(String keyString, Key key) {
        try (PreparedStatement statement = databaseConnection.getConnection(true).prepareStatement(INSERT_STATEMENT)) {

            statement.setString(1, keyString);
            statement.setString(2, key.getType().getIdentifier());
            statement.setLong(3, key.getTime());
            statement.executeUpdate();


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(String keyString) {
        try (PreparedStatement statement = databaseConnection.getConnection(true).prepareStatement(DELETE_STATEMENT)) {

            statement.setString(1, keyString);
            statement.executeUpdate();


        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
