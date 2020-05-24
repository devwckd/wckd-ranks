package co.wckd.ranks.event;

import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.entity.rank.RankType;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public class PlayerLoseRankTimeEvent extends RankEvent {

    private final RankType type;
    private final Source source;
    private final long timeRemaining;
    private final CommandSender executor;
    private final long timeLost;
    private final boolean expired;

    public PlayerLoseRankTimeEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long timeRemaining, CommandSender executor, long timeLost, boolean expired) {
        super(player, rankPlayer);
        this.type = type;
        this.source = source;
        this.timeRemaining = timeRemaining;
        this.executor = executor;
        this.timeLost = timeLost;
        this.expired = expired;
    }

    public CommandSender getExecutor() {
        if (source == Source.TIME) throw new IllegalStateException("Tried to get the executor of a rank that expired.");
        return executor;
    }

    public enum Source {
        COMMAND, TIME
    }

}
