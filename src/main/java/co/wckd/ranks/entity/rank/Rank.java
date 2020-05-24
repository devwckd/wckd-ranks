package co.wckd.ranks.entity.rank;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;
import java.util.UUID;

@Setter
@Getter
@Builder
public class Rank {

    @NonNull
    private final RankType type;
    private Long time;

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
        Rank rank = (Rank) o;
        return Objects.equals(type, rank.type) &&
                Objects.equals(time, rank.time);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type, time);
    }

    public void onActivate(RankPlayer rankPlayer) {

        UUID uuid = rankPlayer.getUuid();
        Player player = Bukkit.getPlayer(uuid);

        type.getCommands().onActivate(player, type);
        type.getItems().onActivate(player, type);
        type.getTitle().onActivate(player, type);
        onChangeTo(rankPlayer);

    }

    public void onChangeTo(RankPlayer rankPlayer) {

        UUID uuid = rankPlayer.getUuid();
        Player player = Bukkit.getPlayer(uuid);

        type.getCommands().onChangeTo(player, type);
        type.getItems().onChangeTo(player, type);
        type.getTitle().onChangeTo(player, type);

    }

    public void onChangedFrom(RankPlayer rankPlayer) {

        UUID uuid = rankPlayer.getUuid();
        Player player = Bukkit.getPlayer(uuid);

        type.getCommands().onChangedFrom(player, type);
        type.getItems().onChangedFrom(player, type);
        type.getTitle().onChangedFrom(player, type);

    }

}
