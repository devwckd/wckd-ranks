package co.wckd.ranks.repository.rankplayer;

import co.wckd.boilerplate.object.DAO;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankPlayer;

import java.util.UUID;

public interface RankPlayerRepository extends DAO<UUID, RankPlayer> {

    void deleteSingle(UUID uuid, Rank rank);

}
