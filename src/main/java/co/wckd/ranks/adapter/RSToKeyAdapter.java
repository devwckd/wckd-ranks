package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.cache.RankTypeCache;
import co.wckd.ranks.entity.key.Key;
import co.wckd.ranks.entity.rank.RankType;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RSToKeyAdapter implements ObjectAdapter<ResultSet, Key> {

    private static final RanksPlugin PLUGIN = RanksPlugin.getInstance();
    private static final RankTypeCache VIP_TYPE_CACHE = PLUGIN.getRankTypeLifecycle().getRankTypeCache();

    @Override
    public Key adapt(ResultSet resultSet) {
        try {

            if (!resultSet.next()) return null;

            RankType vipType = VIP_TYPE_CACHE.find(resultSet.getString("vip_type"));
            if (vipType == null) return null;

            return Key.builder()
                    .key(resultSet.getString("key"))
                    .type(vipType)
                    .time(resultSet.getLong("time"))
                    .build();

        } catch (SQLException exception) {
            exception.printStackTrace();
            return null;
        }
    }
}
