package co.wckd.vips.cache;

import co.wckd.boilerplate.object.DAO;
import co.wckd.vips.entity.VipPlayer;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class VipPlayerCache implements DAO<UUID, VipPlayer> {

    private final Map<UUID, VipPlayer> vipPlayers;

    public VipPlayerCache() {
        this.vipPlayers = new ConcurrentHashMap<>();
    }

    @Override
    public VipPlayer find(UUID uuid) {
        return vipPlayers.get(uuid);
    }

    @Override
    public void insert(UUID uuid, VipPlayer vipPlayer) {
        vipPlayers.put(uuid, vipPlayer);
    }

    @Override
    public void delete(UUID uuid) {
        vipPlayers.remove(uuid);
    }
}
