package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerActivateRankEvent extends PlayerGainRankTimeEvent {

    public PlayerActivateRankEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long time, CommandSender executor, String key) {
        super(player, rankPlayer, type, source, time, executor, key, false);
    }

}
