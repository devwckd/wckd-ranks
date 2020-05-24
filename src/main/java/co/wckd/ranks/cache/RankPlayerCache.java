package co.wckd.ranks.cache;

import co.wckd.boilerplate.object.DAO;
import co.wckd.ranks.entity.RankPlayer;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class RankPlayerCache implements DAO<UUID, RankPlayer> {

    private final Map<UUID, RankPlayer> rankPlayers;

    public RankPlayerCache() {
        this.rankPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public RankPlayer find(UUID uuid) {
        return rankPlayers.get(uuid);
    }

    @Override
    public void insert(UUID uuid, RankPlayer rankPlayer) {
        rankPlayers.put(uuid, rankPlayer);
    }

    @Override
    public void delete(UUID uuid) {
        rankPlayers.remove(uuid);
    }
}
