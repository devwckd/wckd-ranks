package co.wckd.ranks.command;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.key.Key;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.entity.rank.RankType;
import co.wckd.ranks.event.PlayerActivateRankEvent;
import co.wckd.ranks.event.PlayerGainRankTimeEvent;
import co.wckd.ranks.repository.key.KeyRepository;
import co.wckd.ranks.util.Lang;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class UseKeyCommand {

    private final RanksPlugin plugin;
    private final Lang lang;
    private final ExecutorService executorService;
    private final KeyRepository keyRepository;
    private final RankPlayerCache rankPlayerCache;
    private final PluginManager pluginManager;

    public UseKeyCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.lang = plugin.getFileLifecycle().getLang();
        this.executorService = plugin.getExecutorService();
        this.keyRepository = plugin.getKeyLifecycle().getKeyRepository();
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
        this.pluginManager = plugin.getServer().getPluginManager();
    }

    @Command(
            name = "usekey",
            aliases = {"usarkey"},
            inGameOnly = true
    )
    public void onUseKey(
            Execution execution,
            @Argument(nullable = true) String keyString
    ) {

        Player player = execution.getPlayer();

        if (keyString == null) {
            lang.send(player, "usekey_usage");
            return;
        }

        lang.send(player, "usekey_validating");

        executorService.execute(() -> {

            Key key = keyRepository.find(keyString);
            if (key == null) {
                lang.send(player, "usekey_not_found");
                return;
            }

            UUID uniqueId = player.getUniqueId();

            RankPlayer rankPlayer = rankPlayerCache.find(uniqueId);
            if (rankPlayer == null) rankPlayer = new RankPlayer(uniqueId);

            RankType type = key.getType();
            Rank rank = Rank.builder()
                    .type(type)
                    .time(key.getTime())
                    .build();

            PlayerGainRankTimeEvent event;
            if (rankPlayer.hasRank(type)) {
                event = new PlayerGainRankTimeEvent(player, rankPlayer, type, PlayerGainRankTimeEvent.Source.KEY, key.getTime(), null, keyString, true);
            } else {
                event = new PlayerActivateRankEvent(player, rankPlayer, type, PlayerGainRankTimeEvent.Source.KEY, key.getTime(), null, keyString);
            }
            pluginManager.callEvent(event);
            if (event.isCancelled()) return;

            rankPlayer.addRank(rank);
            keyRepository.delete(keyString);
            rankPlayerCache.insert(uniqueId, rankPlayer);

        });

    }

}
