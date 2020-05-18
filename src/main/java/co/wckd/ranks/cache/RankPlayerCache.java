package co.wckd.ranks.cache;

import co.wckd.boilerplate.object.DAO;
import co.wckd.ranks.entity.RankPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RankPlayerCache implements DAO<UUID, RankPlayer> {

    private final Map<UUID, RankPlayer> vipPlayers;

    public RankPlayerCache() {
        this.vipPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public RankPlayer find(UUID uuid) {
        return vipPlayers.get(uuid);
    }

    @Override
    public void insert(UUID uuid, RankPlayer rankPlayer) {
        vipPlayers.put(uuid, rankPlayer);
    }

    @Override
    public void delete(UUID uuid) {
        vipPlayers.remove(uuid);
    }
}
