package co.wckd.ranks.event;

import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

@Getter
public class RankEvent extends Event implements Cancellable {

    private static final HandlerList handlerList = new HandlerList();
    private final Player player;
    private final RankPlayer rankPlayer;
    private final RankType type;
    private boolean cancelled;

    public RankEvent(Player player, RankPlayer rankPlayer, RankType type) {
        this.player = player;
        this.rankPlayer = rankPlayer;
        this.type = type;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        this.cancelled = b;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
