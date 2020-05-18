package co.wckd.vips.command;

import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipPlayerCache;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;
import co.wckd.vips.entity.VipType;
import co.wckd.vips.util.TimeUtils;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import org.bukkit.entity.Player;

public class RankRankCommand {

    private final VipsPlugin plugin;
    private final VipPlayerCache vipPlayerCache;

    public RankRankCommand(VipsPlugin plugin) {
        this.plugin = plugin;
        this.vipPlayerCache = plugin.getVipPlayerLifecycle().getVipPlayerCache();
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
            VipType type,
            @Argument(nullable = true) String timeString
    ) {

        VipPlayer vipPlayer = vipPlayerCache.find(player.getUniqueId());
        if (vipPlayer == null) {
            return;
        }

        Long time = TimeUtils.millisFromString(timeString);
        if (time == null) {
            execution.sendMessage("Wrong time");
            return;
        }

        Vip vip = Vip
                .builder()
                .type(type)
                .time(time)
                .build();

        vip.activate(vipPlayer);

    }

}
