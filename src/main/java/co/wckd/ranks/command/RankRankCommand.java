package co.wckd.ranks.command;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.repository.rankplayer.RankPlayerRepository;
import co.wckd.ranks.util.Lang;
import co.wckd.ranks.util.TimeUtils;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import org.apache.commons.lang3.tuple.Pair;
import org.bukkit.entity.Player;

import java.util.UUID;

public class RankRankCommand {

    private final RanksPlugin plugin;
    private final RankPlayerCache rankPlayerCache;
    private final RankPlayerRepository rankPlayerRepository;
    private final Lang lang;

    public RankRankCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
        this.rankPlayerRepository = plugin.getRankPlayerLifecycle().getRankPlayerRepository();
        this.lang = plugin.getFileLifecycle().getLang();
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
    public void onWRRankGive(
            Execution execution,
            Player player,
            RankType type,
            @Argument(nullable = true) String timeString
    ) {

        UUID uniqueId = player.getUniqueId();

        RankPlayer rankPlayer = rankPlayerCache.find(uniqueId);
        if (rankPlayer == null) {
            rankPlayer = new RankPlayer(uniqueId);
            rankPlayerCache.insert(uniqueId, rankPlayer);
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

        String senderName = execution.getSender() instanceof Player ? execution.getPlayer().getName() : "CONSOLE";
        String rankName = type.getPrettyName().isPresent() ? type.getPrettyName().getSection() : type.getIdentifier();
        lang.send(player, "give_rank",
                Pair.of("{sender}", senderName),
                Pair.of("{type}", rankName),
                Pair.of("{time}", lang.formatTime(time)));

        rankPlayerRepository.insert(uniqueId, rankPlayer);

    }

}
