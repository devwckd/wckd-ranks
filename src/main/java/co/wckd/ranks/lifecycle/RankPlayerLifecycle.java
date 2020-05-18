package co.wckd.ranks.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.repository.rankplayer.RankPlayerRepository;
import co.wckd.ranks.repository.rankplayer.impl.RankPlayerMySQLRepository;
import co.wckd.ranks.repository.rankplayer.impl.RankPlayerSQLiteRepository;
import lombok.Getter;

@Getter
public class RankPlayerLifecycle extends Lifecycle {

    private final RanksPlugin plugin;
    private String type;
    private RankPlayerCache rankPlayerCache;
    private RankPlayerRepository rankPlayerRepository;

    public RankPlayerLifecycle(RanksPlugin plugin) {
        this.plugin = plugin;
        this.type = plugin.getDatabaseLifecycle().getType();
    }

    @Override
    public void enable() {

        type = plugin.getDatabaseLifecycle().getType();
        rankPlayerCache = new RankPlayerCache();
        loadRepository();

    }

    private void loadRepository() {

        if (type == null) {
            return;
        }

        if (type.equalsIgnoreCase("SQLite")) {
            rankPlayerRepository = new RankPlayerSQLiteRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());
            return;
        }

        if (type.equalsIgnoreCase("MySQL")) {
            rankPlayerRepository = new RankPlayerMySQLRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());
            return;
        }

        rankPlayerRepository = new RankPlayerSQLiteRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());

    }

}
