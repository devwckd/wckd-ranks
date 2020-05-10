package co.wckd.vips.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipPlayerCache;
import co.wckd.vips.repository.vipplayer.VipPlayerMySQLRepository;
import co.wckd.vips.repository.vipplayer.VipPlayerRepository;
import co.wckd.vips.repository.vipplayer.VipPlayerSQLiteRepository;
import lombok.Getter;

@Getter
public class VipPlayerLifecycle extends Lifecycle {

    private final VipsPlugin plugin;
    private final String type;
    private VipPlayerCache vipPlayerCache;
    private VipPlayerRepository vipPlayerRepository;

    public VipPlayerLifecycle(VipsPlugin plugin) {
        this.plugin = plugin;
        this.type = plugin.getDatabaseLifecycle().getType();
    }

    @Override
    public void enable() {

        vipPlayerCache = new VipPlayerCache();
        loadRepository();

    }

    private void loadRepository() {

        if (type == null) {
            return;
        }

        if (type.equalsIgnoreCase("SQLite")) {
            vipPlayerRepository = new VipPlayerSQLiteRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());
            return;
        }

        if (type.equalsIgnoreCase("MySQL")) {
            vipPlayerRepository = new VipPlayerMySQLRepository(plugin, plugin.getDatabaseLifecycle().getDatabaseConnection());
            return;
        }

    }

}
