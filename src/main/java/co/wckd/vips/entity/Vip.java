package co.wckd.vips.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Vip {

    private final VipType type;
    private Long time;

    public void activate(VipPlayer vipPlayer) {

        UUID uuid = vipPlayer.getUuid();
        Player player = Bukkit.getPlayer(uuid);

        type.getPermissions().apply(player, type);
        type.getCommands().apply(player, type);
        type.getItems().apply(player, type);
        type.getTitle().apply(player, type);

        vipPlayer.addVip(type.getIdentifier(), this);

    }

    public void increaseTime(Long time) {
        this.time += time;
    }

    public void reduceTime(Long time) {
        this.time -= time;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vip vip = (Vip) o;
        return Objects.equals(type, vip.type) &&
                Objects.equals(time, vip.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, time);
    }
}
