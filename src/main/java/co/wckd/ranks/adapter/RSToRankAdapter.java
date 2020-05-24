package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankTypeCache;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankType;

import java.sql.ResultSet;

public class RSToRankAdapter implements ObjectAdapter<ResultSet, Rank> {

    private static final RanksPlugin PLUGIN = RanksPlugin.getInstance();
    private static final RankTypeCache VIP_TYPE_CACHE = PLUGIN.getRankTypeLifecycle().getRankTypeCache();

    @Override
    public Rank adapt(ResultSet resultSet) {
        try {

            RankType type = VIP_TYPE_CACHE.find(resultSet.getString("type"));
            long time = resultSet.getLong("time");

            return Rank
                    .builder()
                    .type(type)
                    .time(time)
                    .build();

        } catch (Exception exception) {
            return null;
        }
    }

}
