package co.wckd.vips.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipPlayerCache;
import co.wckd.vips.database.DatabaseConnection;
import co.wckd.vips.database.impl.MySQLConnection;
import co.wckd.vips.database.impl.SQLiteConnection;
import co.wckd.vips.repository.DatabaseRepository;
import co.wckd.vips.repository.MySQLRepository;
import co.wckd.vips.repository.SQLiteRepository;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@Getter
public class DatabaseLifecycle extends Lifecycle {

    private final VipsPlugin plugin;
    private FileConfiguration configuration;
    private VipPlayerCache vipPlayerCache;
    private DatabaseConnection databaseConnection;
    private DatabaseRepository databaseRepository;

    public DatabaseLifecycle(VipsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {

        configuration = plugin.getFileLifecycle().getConfiguration();
        vipPlayerCache = new VipPlayerCache();
        loadDatabase();

    }

    private void loadDatabase() {

        String type = configuration.getString("storage.type");
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

        plugin.log("No database type found, using SQLite.");
        useSQLite();

    }

    private void useSQLite() {
        String schema = plugin.getConfig().getString("database.schema");
        databaseConnection = new SQLiteConnection(new File(plugin.getDataFolder(), schema + ".db"));
        databaseRepository = new SQLiteRepository(plugin, databaseConnection);
    }

    private void useMySQL() {
        databaseConnection = new MySQLConnection(
                configuration.getString("database.hostname"),
                configuration.getString("database.username"),
                configuration.getString("database.password"),
                configuration.getString("database.schema")
        );
        databaseRepository = new MySQLRepository(plugin, databaseConnection);
    }

}