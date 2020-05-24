package co.wckd.ranks.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.repository.key.KeyRepository;
import co.wckd.ranks.repository.key.impl.KeyMySQLRepository;
import co.wckd.ranks.repository.key.impl.KeySQLiteRepository;
import lombok.Getter;

@Getter
public class KeyLifecycle extends Lifecycle {

    private final RanksPlugin plugin;
    private String type;
    private KeyRepository keyRepository;

    public KeyLifecycle(RanksPlugin plugin) {
        this.plugin = plugin;
        this.type = plugin.getDatabaseLifecycle().getType();
    }

    @Override
    public void enable() {

        type = plugin.getDatabaseLifecycle().getType();
        loadRepository();

    }

    private void loadRepository() {

        if (type == null) {
            return;
        }

        if (type.equalsIgnoreCase("SQLite")) {
            keyRepository = new KeySQLiteRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());
            return;
        }

        if (type.equalsIgnoreCase("MySQL")) {
            keyRepository = new KeyMySQLRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());
            return;
        }

        keyRepository = new KeySQLiteRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());

    }

}
