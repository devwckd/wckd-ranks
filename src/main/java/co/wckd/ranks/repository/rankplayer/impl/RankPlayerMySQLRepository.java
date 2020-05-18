package co.wckd.ranks.repository.rankplayer.impl;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.database.DatabaseConnection;
import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.repository.rankplayer.RankPlayerRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.UUID;

public class RankPlayerMySQLRepository implements RankPlayerRepository {

    private static final String FIND_STATEMENT = "SELECT * FROM vips WHERE uuid = ?;";
    private static final String INSERT_STATEMENT = "REPLACE INTO vips VALUES (?, ?, ?, ?);";
    private static final String DELETE_STATEMENT = "DELETE FROM vips WHERE uuid = ?;";
    private static final String DELETE_SINGLE_STATEMENT = "DELETE FROM vips WHERE uuid = ? AND vip_type = ?;";

    private final RanksPlugin plugin;
    private final Adapter adapter;
    private final DatabaseConnection databaseConnection;

    public RankPlayerMySQLRepository(RanksPlugin plugin, DatabaseConnection databaseConnection) {
        this.plugin = plugin;
        this.adapter = plugin.getAdapter();
        this.databaseConnection = databaseConnection;
    }

    @Override
    public RankPlayer find(UUID uuid) {
        try (PreparedStatement statement = databaseConnection.getConnection(true).prepareStatement(FIND_STATEMENT)) {

            statement.setString(1, uuid.toString());
            ResultSet resultSet = statement.executeQuery();
            return adapter.adapt(resultSet, ResultSet.class, RankPlayer.class);

        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    @Override
    public void insert(UUID uuid, RankPlayer rankPlayer) {
        try (Connection connection = databaseConnection.getConnection(false);
             PreparedStatement statement = connection.prepareStatement(INSERT_STATEMENT)) {

            for (Rank rank : rankPlayer.getRanks().values()) {
                statement.setString(1, uuid.toString());
                statement.setString(2, rank.getType().getIdentifier());
                statement.setLong(3, rank.getTime());
                statement.setBoolean(4, (rankPlayer.getActive() != null && rankPlayer.getActive().equals(rank)));
                statement.addBatch();
            }

            statement.executeBatch();
            connection.setAutoCommit(true);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void delete(UUID uuid) {
        try (Connection connection = databaseConnection.getConnection(true);
             PreparedStatement statement = connection.prepareStatement(DELETE_STATEMENT)) {

            statement.setString(1, uuid.toString());
            statement.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    @Override
    public void deleteSingle(UUID uuid, Rank rank) {
        try (Connection connection = databaseConnection.getConnection(true);
             PreparedStatement statement = connection.prepareStatement(DELETE_SINGLE_STATEMENT)) {

            statement.setString(1, uuid.toString());
            statement.setString(2, rank.getType().getIdentifier());
            statement.executeUpdate();

        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }
}
