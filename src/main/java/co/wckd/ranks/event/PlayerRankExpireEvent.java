package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import org.bukkit.entity.Player;

public class PlayerRankExpireEvent extends PlayerLoseRankTimeEvent {
    public PlayerRankExpireEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long timeRemaining, Player executor, long timeLost, boolean expired) {
        super(player, rankPlayer, type, source, timeRemaining, executor, timeLost, true);
    }
}
