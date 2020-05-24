package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import org.bukkit.entity.Player;

public class PlayerActivateRankEvent extends PlayerGainRankTimeEvent {

    public PlayerActivateRankEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long time, Player executor, String key) {
        super(player, rankPlayer, type, source, time, executor, key, false);
    }

}
