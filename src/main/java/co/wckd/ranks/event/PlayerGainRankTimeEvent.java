package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import org.bukkit.entity.Player;

public class PlayerGainRankTimeEvent extends RankEvent {

    private final Source source;
    private final long time;
    private final Player executor;
    private final String key;
    private final boolean isIncrease;

    public PlayerGainRankTimeEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long time, Player executor, String key, boolean isIncrease) {
        super(player, rankPlayer, type);
        this.source = source;
        this.time = time;
        this.executor = executor;
        this.key = key;
        this.isIncrease = isIncrease;
    }

    public enum Source {
        COMMAND, KEY
    }

}
