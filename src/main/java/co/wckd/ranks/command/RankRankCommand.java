package co.wckd.ranks.command;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.util.TimeUtils;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import org.bukkit.entity.Player;

public class RankRankCommand {

    private final RanksPlugin plugin;
    private final RankPlayerCache rankPlayerCache;

    public RankRankCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
    }

    @Command(
            name = "wickedranks.rank"
    )
    public void onWVRankCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDRANKS §8- §fRanks Help.",
                " ",
                " §8➟ §e/wr rank give <player> <group> [duration] §8- §fGives a VIP rank to the player.",
                " §8➟ §e/wr rank remove <player> <group> [duration] §8- §fRemoves a VIP rank from the player.",
                " "
        });

    }

    @Command(
            name = "wickedranks.rank.give"
    )
    public void onWVRankGive(
            Execution execution,
            Player player,
            RankType type,
            @Argument(nullable = true) String timeString
    ) {

        RankPlayer rankPlayer = rankPlayerCache.find(player.getUniqueId());
        if (rankPlayer == null) {
            return;
        }

        Long time = TimeUtils.millisFromString(timeString);
        if (time == null) {
            execution.sendMessage("Wrong time");
            return;
        }

        Rank rank = Rank
                .builder()
                .type(type)
                .time(time)
                .build();

        rank.activate(rankPlayer);

    }

}
