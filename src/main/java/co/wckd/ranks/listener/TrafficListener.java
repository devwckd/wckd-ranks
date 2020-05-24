package co.wckd.ranks.listener;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.repository.rankplayer.RankPlayerRepository;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class TrafficListener implements Listener {

    private final RanksPlugin plugin;
    private final ExecutorService executorService;
    private final RankPlayerRepository rankPlayerRepository;
    private final RankPlayerCache rankPlayerCache;

    public TrafficListener(RanksPlugin plugin) {
        this.plugin = plugin;
        this.executorService = plugin.getExecutorService();
        this.rankPlayerRepository = plugin.getRankPlayerLifecycle().getRankPlayerRepository();
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        executorService.execute(() -> {

            Player player = event.getPlayer();
            UUID uniqueId = player.getUniqueId();

            RankPlayer rankPlayer = rankPlayerRepository.find(uniqueId);
            if (rankPlayer == null) return;

            rankPlayerCache.insert(uniqueId, rankPlayer);

        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        executorService.execute(() -> {

            Player player = event.getPlayer();
            UUID uniqueId = player.getUniqueId();

            RankPlayer rankPlayer = rankPlayerCache.find(uniqueId);
            rankPlayerRepository.insert(uniqueId, rankPlayer);

            rankPlayerCache.delete(uniqueId);

        });
    }

}
