package co.wckd.vips.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Setter
@Getter
public class VipPlayer {

    @Singular
    private Map<String, Vip> vips;

    public VipPlayer() {
        this.vips = new ConcurrentHashMap<>();
    }

    public void addVip(String identifier, Vip vip) {
        vips.put(identifier, vip);
    }

}
