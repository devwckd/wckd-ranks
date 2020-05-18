package co.wckd.ranks.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class RankPlayer {

    private final UUID uuid;
    private final Map<String, Rank> ranks;
    private Rank active;

    public RankPlayer(UUID uuid) {
        this.uuid = uuid;
        this.ranks = new ConcurrentHashMap<>();
        this.active = null;
    }

    public boolean hasRank(RankType type) {
        return hasRank(type.getIdentifier());
    }

    public boolean hasRank(String identifier) {
        return ranks.containsKey(identifier);
    }

    public void addRank(Rank rank) {
        addRank(rank.getType().getIdentifier(), rank);
    }

    public void addRank(String identifier, Rank rank) {
        if (active == null) active = rank;

        rank.activate(this);
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

    public void addRanks(Map<String, Rank> rankMap) {
        ranks.putAll(rankMap);
    }

}
