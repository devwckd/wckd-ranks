package co.wckd.ranks.entity.key;

import co.wckd.ranks.entity.rank.RankType;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Key {

    private final String key;
    private final RankType type;
    private final long time;

}
