package co.wckd.ranks.event;

import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.entity.rank.RankType;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerRankExpireEvent extends PlayerLoseRankTimeEvent {
    public PlayerRankExpireEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long timeRemaining, CommandSender executor, long timeLost) {
        super(player, rankPlayer, type, source, timeRemaining, executor, timeLost, true);
    }
}
