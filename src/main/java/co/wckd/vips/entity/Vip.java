package co.wckd.vips.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Builder
public class Vip {

    private final UUID uuid;
    private final VipType type;
    private Long time;

    private void increaseTime(Long time) {
        this.time += time;
    }

    private void reduceTime(Long time) {
        this.time -= time;
    }

}
