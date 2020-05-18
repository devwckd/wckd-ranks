package co.wckd.ranks.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.database.DatabaseConnection;
import co.wckd.ranks.database.impl.MySQLConnection;
import co.wckd.ranks.database.impl.SQLiteConnection;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@Getter
public class DatabaseLifecycle extends Lifecycle {

    private final RanksPlugin plugin;
    private FileConfiguration configuration;
    private String type;
    private DatabaseConnection databaseConnection;

    public DatabaseLifecycle(RanksPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        configuration = plugin.getFileLifecycle().getConfiguration();
        loadDatabase();

    }

    private void loadDatabase() {

        type = configuration.getString("database.type");
        if (type == null) {
            return;
        }

        if (type.equalsIgnoreCase("SQLite")) {
            useSQLite();
            return;
        }

        if (type.equalsIgnoreCase("MySQL")) {
            useMySQL();
            return;
        }

        // TODO: info
        useSQLite();

    }

    private void useSQLite() {
        String schema = plugin.getConfig().getString("database.schema");
        databaseConnection = new SQLiteConnection(new File(plugin.getDataFolder(), schema + ".db"));
        databaseConnection.openConnection();
    }

    private void useMySQL() {
        databaseConnection = new MySQLConnection(
                configuration.getString("database.hostname"),
                configuration.getString("database.username"),
                configuration.getString("database.password"),
                configuration.getString("database.schema")
        );
        databaseConnection.openConnection();
    }

}
