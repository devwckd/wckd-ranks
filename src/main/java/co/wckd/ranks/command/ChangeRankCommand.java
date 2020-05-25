package co.wckd.ranks.command;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.event.PlayerChangeRankEvent;
import co.wckd.ranks.util.Lang;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

public class ChangeRankCommand {

    private final RanksPlugin plugin;
    private final RankPlayerCache rankPlayerCache;
    private final PluginManager pluginManager;
    private final Lang lang;

    public ChangeRankCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
        this.pluginManager = plugin.getServer().getPluginManager();
        this.lang = plugin.getFileLifecycle().getLang();
    }

    @Command(
            name = "changerank",
            aliases = {"trocarvip"},
            inGameOnly = true
    )
    public void onChangeRankCommand(Execution execution) {

        Player player = execution.getPlayer();
        String[] args = execution.getArgs();

        if (args.length != 1) {
            lang.send(player, "changerank_usage");
            return;
        }

        RankPlayer rankPlayer = rankPlayerCache.find(player.getUniqueId());
        if (rankPlayer == null || rankPlayer.getRanks().isEmpty()) {
            lang.send(player, "changerank_no_ranks");
            return;
        }

        String type = args[0];
        if (!rankPlayer.hasRank(type)) {
            lang.send(player, "changerank_no_rank");
            return;
        }

        Rank active = rankPlayer.getActive();
        if (active.getType().getIdentifier().equalsIgnoreCase(type)) {
            lang.send(player, "changerank_already_active");
            return;
        }

        PlayerChangeRankEvent playerChangeRankEvent = new PlayerChangeRankEvent(player, rankPlayer, active, rankPlayer.getRank(type), player);
        pluginManager.callEvent(playerChangeRankEvent);
        if (playerChangeRankEvent.isCancelled()) return;

        rankPlayer.setActive(type);

    }

}
