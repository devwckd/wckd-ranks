package co.wckd.vips.entity;

import lombok.Getter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Getter
public class VipPlayer {

    private final Map<String, Vip> vips;

    public VipPlayer() {
        this.vips = new ConcurrentHashMap<>();
    }

}
