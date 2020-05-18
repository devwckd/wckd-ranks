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
    private final Map<String, Rank> vips;
    private Rank active;

    public RankPlayer(UUID uuid) {
        this.uuid = uuid;
        this.vips = new ConcurrentHashMap<>();
        this.active = null;
    }

    public void addVip(String identifier, Rank rank) {
        if (active == null) active = rank;

        if (vips.containsKey(identifier)) {
            vips.get(identifier).increaseTime(rank.getTime());
            return;
        }

        vips.put(identifier, rank);
    }

    public void addVips(Map<String, Rank> vipMap) {
        vips.putAll(vipMap);
    }

}
