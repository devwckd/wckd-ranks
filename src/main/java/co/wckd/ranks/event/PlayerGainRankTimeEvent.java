package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public class PlayerGainRankTimeEvent extends RankEvent {

    private final RankType type;
    private final Source source;
    private final long time;
    private final CommandSender executor;
    private final String key;
    private final boolean isIncrease;

    public PlayerGainRankTimeEvent(Player player, RankPlayer rankPlayer, RankType type, Source source, long time, CommandSender executor, String key, boolean isIncrease) {
        super(player, rankPlayer);
        this.type = type;
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
