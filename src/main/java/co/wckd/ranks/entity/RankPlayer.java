package co.wckd.ranks.entity;

import co.wckd.ranks.event.PlayerLoseRankTimeEvent;
import co.wckd.ranks.event.PlayerRankExpireEvent;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class RankPlayer {

    private final UUID uuid;
    private final Map<String, Rank> ranks;
    private Rank active;
    private long joined;

    public RankPlayer(UUID uuid) {
        this.uuid = uuid;
        this.ranks = new ConcurrentHashMap<>();
        this.active = null;
        this.joined = System.currentTimeMillis();
    }

    public boolean hasRank(RankType type) {
        return hasRank(type.getIdentifier());
    }

    public boolean hasRank(String identifier) {
        return ranks.containsKey(identifier.toLowerCase());
    }

    public void addRank(Rank rank) {
        addRank(rank.getType().getIdentifier(), rank);
    }

    public void addRank(String identifier, Rank rank) {
        if (active == null) active = rank;

        rank.onActivate(this);
        if (ranks.containsKey(identifier)) {
            ranks.get(identifier).increaseTime(rank.getTime());
            return;
        }

        ranks.put(identifier, rank);
    }

    public boolean removeRank(Rank rank) {
        return removeRank(rank.getType().getIdentifier(), rank);
    }

    public boolean removeRank(String identifier, Rank rank) {

        if (rank.getTime() == -1) {
            ranks.remove(identifier);
            return true;
        }

        Rank playerRank = ranks.get(identifier);
        if (playerRank.getTime() - rank.getTime() < 1) {
            ranks.remove(identifier);
            return true;
        }

        playerRank.setTime(playerRank.getTime() - rank.getTime());
        return false;

    }

    public void removeRank(String identifier, long time) {

        Rank rank = ranks.get(identifier);
        if (rank == null) return;

        if (time == -1 || rank.getTime() - time < 1) {

            if (rank.equals(active)) {

            }
            ranks.remove(identifier);


        }
    }

    public void addRanks(Map<String, Rank> rankMap) {
        ranks.putAll(rankMap);
    }

    public void setActive(String identifier) {
        if (!ranks.containsKey(identifier)) return;
        active = ranks.get(identifier);
    }

    public void tick(long now, long time) {

        if (active.getTime() == -1) return;

        active.reduceTime(Math.min(now - joined, time * 1000));

        if (active.getTime() < 1) {
            PlayerRankExpireEvent expireEvent = new PlayerRankExpireEvent(
                    Bukkit.getPlayer(uuid),
                    this,
                    active.getType(),
                    PlayerLoseRankTimeEvent.Source.TIME,
                    0,
                    null,
                    60,
                    true);

            Bukkit.getPluginManager().callEvent(expireEvent);

            if (!expireEvent.isCancelled()) return;

        }
    }
}
