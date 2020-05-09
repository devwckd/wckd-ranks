package co.wckd.vips.entity;

import co.wckd.vips.entity.section.*;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class VipType {

    private final String identifier;

    private final PrettyName prettyName;
    private final Permissions permissions;
    private final Commands commands;
    private final Items items;
    private final Title title;

}
