package co.wckd.vips.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class VipPlayer {

    private final UUID uuid;
    private final Map<String, Vip> vips;
    private Vip active;

    public VipPlayer(UUID uuid) {
        this.uuid = uuid;
        this.vips = new ConcurrentHashMap<>();
        this.active = null;
    }

    public void addVip(String identifier, Vip vip) {
        if (active == null) active = vip;

        if (vips.containsKey(identifier)) {
            vips.get(identifier).increaseTime(vip.getTime());
            return;
        }

        vips.put(identifier, vip);
    }

    public void addVips(Map<String, Vip> vipMap) {
        vips.putAll(vipMap);
    }

}
