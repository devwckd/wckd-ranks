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

public class WRRankCommand {

    private final RanksPlugin plugin;
    private final RankPlayerCache rankPlayerCache;
    private final RankPlayerRepository rankPlayerRepository;
    private final Lang lang;

    public WRRankCommand(RanksPlugin plugin) {
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
            execution.sendMessage("§6§lWICKEDRANKS §8➟ §cIncorrect time string.");
            return;
        }

        Rank rank = Rank
                .builder()
                .type(type)
                .time(time)
                .build();

        rankPlayer.addRank(rank);

        String messageToSend = time == -1 ? "give_lifetime_rank" : "give_rank";
        String senderName = execution.getSender() instanceof Player ? execution.getPlayer().getName() : "CONSOLE";

        lang.send(player, messageToSend,
                Pair.of("{player}", senderName),
                Pair.of("{sender}", player.getName()),
                Pair.of("{pretty_name}", type.getPrettyName()),
                Pair.of("{identifier}", type.getIdentifier()),
                Pair.of("{time}", lang.formatTime(time)));


        rankPlayerRepository.insert(uniqueId, rankPlayer);

    }

    @Command(
            name = "wickedranks.rank.remove"
    )
    public void onWRRankRemove(
            Execution execution,
            Player player,
            RankType type,
            @Argument(nullable = true) String timeString
    ) {

        UUID uniqueId = player.getUniqueId();

        RankPlayer rankPlayer = rankPlayerCache.find(uniqueId);
        if (rankPlayer == null) {
            execution.sendMessage("§6§lWICKEDRANKS §8➟ §cThis player doesn't have this rank.");
            return;
        }

        if (!rankPlayer.hasRank(type)) {
            execution.sendMessage("§6§lWICKEDRANKS §8➟ §cThis player doesn't have this rank.");
            return;
        }

        Long time = TimeUtils.millisFromString(timeString);
        if (time == null) {
            execution.sendMessage("§6§lWICKEDRANKS §8➟ §cIncorrect time string.");
            return;
        }

        Rank rank = Rank
                .builder()
                .type(type)
                .time(time)
                .build();

        boolean isLifetime = rankPlayer.removeRank(rank);
        String messageToSend = isLifetime ? "remove_lifetime_rank" : "remove_rank";
        String senderName = execution.getSender() instanceof Player ? execution.getPlayer().getName() : "CONSOLE";

        lang.send(player, messageToSend,
                Pair.of("{player}", senderName),
                Pair.of("{sender}", player.getName()),
                Pair.of("{pretty_name}", type.getPrettyName()),
                Pair.of("{identifier}", type.getIdentifier()),
                Pair.of("{time}", lang.formatTime(time)));

        if (isLifetime) {
            rankPlayerRepository.deleteSingle(uniqueId, rank);
        } else {
            rankPlayerRepository.insert(uniqueId, rankPlayer);
        }

    }

}
