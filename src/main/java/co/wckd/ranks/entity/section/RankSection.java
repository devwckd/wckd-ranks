package co.wckd.ranks.entity.section;

import co.wckd.ranks.entity.RankType;
import lombok.Getter;
import org.bukkit.entity.Player;

@Getter
public abstract class RankSection<T> {

    private T onActivateSection;
    private boolean isActivatePresent = false;
    private T onChangeToSection;
    private boolean isChangeToPresent = false;
    private T onChangedFromSection;
    private boolean isChangedFromPresent = false;

    public void setOnActivateSection(T onActivateSection) {
        if (onActivateSection == null) return;
        this.onActivateSection = onActivateSection;
        this.isActivatePresent = true;
    }

    public void setOnChangeToSection(T onChangeToSection) {
        if (onActivateSection == null) return;
        this.onChangeToSection = onChangeToSection;
        this.isChangeToPresent = true;
    }

    public void setOnChangedFromSection(T onChangedFromSection) {
        if (onActivateSection == null) return;
        this.onChangedFromSection = onChangedFromSection;
        this.isChangedFromPresent = true;
    }

    public abstract void onActivate(Player player, RankType type);

    public abstract void onChangeTo(Player player, RankType type);

    public abstract void onChangedFrom(Player player, RankType type);

}
