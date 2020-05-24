package co.wckd.ranks.event;

import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;
import lombok.Getter;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

@Getter
public class PlayerChangeRankEvent extends RankEvent {

    private final Rank from;
    private final Rank to;
    private final CommandSender executor;

    public PlayerChangeRankEvent(Player player, RankPlayer rankPlayer, Rank from, Rank to, CommandSender executor) {
        super(player, rankPlayer);
        this.from = from;
        this.to = to;
        this.executor = executor;
    }

}
