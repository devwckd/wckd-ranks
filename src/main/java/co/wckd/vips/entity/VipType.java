package co.wckd.vips.entity;

import co.wckd.vips.entity.section.*;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class VipType {

    private final String identifier;

    private final PrettyName prettyName;
    private final Permissions permissions;
    private final Commands commands;
    private final Items items;
    private final Title title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        VipType type = (VipType) o;
        return identifier.equals(type.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }
}
