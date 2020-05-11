package co.wckd.vips.entity;

import co.wckd.vips.entity.section.PrettyName;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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

    private void increaseTime(Long time) {
        this.time += time;
    }

    private void reduceTime(Long time) {
        this.time -= time;
    }

    private String prepareMessage(String message, Player player) {

        PrettyName prettyName = type.getPrettyName();
        String prettyNameString = prettyName.isPresent() ? prettyName.getSection() : type.getIdentifier();

        return message
                .replace("{player}", player.getName())
                .replace("{name}", type.getIdentifier())
                .replace("{pretty_name}", prettyNameString);

    }

}
