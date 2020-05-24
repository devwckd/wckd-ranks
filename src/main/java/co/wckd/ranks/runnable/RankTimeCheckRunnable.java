package co.wckd.ranks.runnable;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.util.Lang;

import java.util.Map;
import java.util.UUID;

public class RankTimeCheckRunnable implements Runnable {

    private final RanksPlugin plugin;
    private final Map<UUID, RankPlayer> rankPlayers;
    private final long delay;
    private final Lang lang;

    public RankTimeCheckRunnable(RanksPlugin plugin, long delay) {
        this.plugin = plugin;
        this.rankPlayers = plugin.getRankPlayerLifecycle().getRankPlayerCache().getRankPlayers();
        this.delay = delay;
        this.lang = plugin.getFileLifecycle().getLang();
    }

    @Override
    public void run() {

        long now = System.currentTimeMillis();

        for (RankPlayer rankPlayer : rankPlayers.values()) {
            rankPlayer.tick(now, delay);
        }

    }

}
