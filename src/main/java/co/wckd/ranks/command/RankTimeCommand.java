package co.wckd.ranks.command;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.util.Lang;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

public class RankTimeCommand {

    private final RanksPlugin plugin;
    private final RankPlayerCache rankPlayerCache;
    private final Lang lang;

    public RankTimeCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
        this.lang = plugin.getFileLifecycle().getLang();
    }

    @Command(
            name = "ranktime",
            aliases = {"tempovip"},
            inGameOnly = true
    )
    public void onRankTimeCommand(Execution execution) {

        Player player = execution.getPlayer();
        RankPlayer rankPlayer = rankPlayerCache.find(player.getUniqueId());
        if (rankPlayer == null || rankPlayer.getRanks().isEmpty()) {
            lang.send(player, "ranktime_no_ranks");
            return;
        }

        lang.send(player, "ranktime_header");

        for (Rank rank : rankPlayer.getRanks().values()) {

            String messageToSend = "ranktime_line";
            if (rank.equals(rankPlayer.getActive())) {
                messageToSend = "ranktime_line_active";
            }

            lang.send(player, messageToSend,
                    Pair.of("{sender}", player.getName()),
                    Pair.of("{pretty_name}", rank.getType().getPrettyName()),
                    Pair.of("{identifier}", rank.getType().getIdentifier()),
                    Pair.of("{time}", lang.formatTime(rank.getTime())));

        }

        lang.send(player, "ranktime_footer");

    }

}
