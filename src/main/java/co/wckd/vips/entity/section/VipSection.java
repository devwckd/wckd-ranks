package co.wckd.vips.entity.section;

import co.wckd.vips.entity.VipType;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class VipSection<T> {

    private T section;
    private boolean isPresent = false;

    public void setSection(T section) {
        this.section = section;
        this.isPresent = section != null;
    }

    public abstract void apply(Player player, VipType type);

}
