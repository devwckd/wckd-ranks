package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import org.bukkit.entity.Player;

public class PlayerLoseRankTimeEvent extends RankEvent {

    private final Source source;
    private final long timeRemaining;
    private final Player executor;
    private final long timeLost;
    private final boolean expired;

    public PlayerLoseRankTimeEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long timeRemaining, Player executor, long timeLost, boolean expired) {
        super(player, rankPlayer, type);
        this.source = source;
        this.timeRemaining = timeRemaining;
        this.executor = executor;
        this.timeLost = timeLost;
        this.expired = expired;
    }

    public Player getExecutor() {
        if (source == Source.TIME) throw new IllegalStateException("Tried to get the executor of a rank that expired.");
        return executor;
    }

    public enum Source {
        COMMAND, TIME
    }

}
