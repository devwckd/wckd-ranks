package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankPlayer;

import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class RSToRankPlayerAdapter implements ObjectAdapter<ResultSet, RankPlayer> {

    private static final RanksPlugin PLUGIN = RanksPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public RankPlayer adapt(ResultSet resultSet) {
        try {

            Map<String, Rank> vips = new ConcurrentHashMap<>();
            Rank active = null;

            String uuid = null;
            while (resultSet.next()) {

                Rank rank = ADAPTER.adapt(resultSet, ResultSet.class, Rank.class);
                if (rank == null) continue;
                vips.put(rank.getType().getIdentifier(), rank);

                if (resultSet.getBoolean("is_active") && active == null)
                    active = rank;

                uuid = resultSet.getString("uuid");
            }

            if (vips.isEmpty() || uuid == null || active == null) return null;

            RankPlayer rankPlayer = new RankPlayer(UUID.fromString(uuid));
            rankPlayer.addRanks(vips);
            rankPlayer.setActive(active.getType().getIdentifier());
            return rankPlayer;

        } catch (Exception exception) {
            return null;
        }
    }

}
