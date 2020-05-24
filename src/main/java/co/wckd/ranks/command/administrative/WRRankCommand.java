package co.wckd.ranks.command.administrative;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankPlayerCache;
import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.event.PlayerActivateRankEvent;
import co.wckd.ranks.event.PlayerGainRankTimeEvent;
import co.wckd.ranks.event.PlayerLoseRankTimeEvent;
import co.wckd.ranks.event.PlayerRankExpireEvent;
import co.wckd.ranks.repository.rankplayer.RankPlayerRepository;
import co.wckd.ranks.util.Lang;
import co.wckd.ranks.util.TimeUtils;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.UUID;
import java.util.concurrent.ExecutorService;

public class WRRankCommand {

    private final RanksPlugin plugin;
    private final RankPlayerCache rankPlayerCache;
    private final RankPlayerRepository rankPlayerRepository;
    private final Lang lang;
    private final ExecutorService executorService;
    private final PluginManager pluginManager;

    public WRRankCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.rankPlayerCache = plugin.getRankPlayerLifecycle().getRankPlayerCache();
        this.rankPlayerRepository = plugin.getRankPlayerLifecycle().getRankPlayerRepository();
        this.lang = plugin.getFileLifecycle().getLang();
        this.executorService = plugin.getExecutorService();
        this.pluginManager = plugin.getServer().getPluginManager();
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

        PlayerGainRankTimeEvent event;
        if (rankPlayer.hasRank(type)) {
            event = new PlayerGainRankTimeEvent(player, rankPlayer, type, PlayerGainRankTimeEvent.Source.COMMAND, time, execution.getSender(), null, true);
        } else {
            event = new PlayerActivateRankEvent(player, rankPlayer, type, PlayerGainRankTimeEvent.Source.COMMAND, time, execution.getSender(), null);
        }
        pluginManager.callEvent(event);
        if (event.isCancelled()) return;

        rankPlayer.addRank(rank);

        RankPlayer finalRankPlayer = rankPlayer;
        executorService.execute(() -> rankPlayerRepository.insert(uniqueId, finalRankPlayer));

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

        boolean isExpired = time == -1 || (rankPlayer.hasRank(type) && rankPlayer.getRank(type).getTime() - time < 1);

        PlayerLoseRankTimeEvent event;
        if (rankPlayer.hasRank(type)) {
            event = new PlayerLoseRankTimeEvent(player, rankPlayer, type, PlayerLoseRankTimeEvent.Source.COMMAND, rank.getTime() - time, execution.getSender(), time, isExpired);
        } else {
            event = new PlayerRankExpireEvent(player, rankPlayer, type, PlayerLoseRankTimeEvent.Source.COMMAND, 0, execution.getSender(), rank.getTime());
        }
        pluginManager.callEvent(event);
        if (event.isCancelled()) return;

        rankPlayer.removeRank(rank);

        executorService.execute(() -> {
            if (isExpired) {
                rankPlayerRepository.deleteSingle(uniqueId, rank);
            } else {
                rankPlayerRepository.insert(uniqueId, rankPlayer);
            }
        });

    }

}
