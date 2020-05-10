package co.wckd.vips.listener;

import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipPlayerCache;
import co.wckd.vips.entity.VipPlayer;
import co.wckd.vips.repository.DatabaseRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class TrafficListener implements Listener {

    private final VipsPlugin plugin;
    private final ExecutorService executorService;
    private final DatabaseRepository databaseRepository;
    private final VipPlayerCache vipPlayerCache;

    public TrafficListener(VipsPlugin plugin) {
        this.plugin = plugin;
        this.executorService = plugin.getExecutorService();
        this.databaseRepository = plugin.getDatabaseLifecycle().getDatabaseRepository();
        this.vipPlayerCache = plugin.getDatabaseLifecycle().getVipPlayerCache();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        executorService.execute(() -> {

            Player player = event.getPlayer();
            UUID uniqueId = player.getUniqueId();

            VipPlayer vipPlayer = databaseRepository.find(uniqueId);
            if (vipPlayer == null) vipPlayer = new VipPlayer();

            vipPlayerCache.insert(uniqueId, vipPlayer);

        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        executorService.execute(() -> {

            Player player = event.getPlayer();
            UUID uniqueId = player.getUniqueId();

            VipPlayer vipPlayer = vipPlayerCache.find(uniqueId);
            databaseRepository.insert(uniqueId, vipPlayer);

            vipPlayerCache.delete(uniqueId);

        });
    }

}