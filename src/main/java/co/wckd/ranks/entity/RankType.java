package co.wckd.ranks.entity;

import co.wckd.ranks.entity.section.Commands;
import co.wckd.ranks.entity.section.Items;
import co.wckd.ranks.entity.section.Messages;
import co.wckd.ranks.entity.section.Title;
import lombok.Builder;
import lombok.Getter;

import java.util.Objects;

@Getter
@Builder
public class RankType {

    private final String identifier;
    private final String prettyName;

    private final Messages messages;
    private final Commands commands;
    private final Items items;
    private final Title title;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        RankType type = (RankType) o;
        return identifier.equals(type.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    public String getPrettyName() {
        return prettyName;
    }

}
