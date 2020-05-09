package co.wckd.vips.entity.section;

import lombok.Getter;

@Getter
public class VipSection<T> {

    private T section;
    private boolean isPresent = false;

    public void setSection(T section) {
        this.section = section;
        this.isPresent = section != null;
    }
}
