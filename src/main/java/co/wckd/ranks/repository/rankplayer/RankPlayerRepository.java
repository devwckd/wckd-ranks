package co.wckd.ranks.repository.rankplayer;

import co.wckd.boilerplate.object.DAO;
import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;

import java.util.UUID;

public interface RankPlayerRepository extends DAO<UUID, RankPlayer> {

    void deleteSingle(UUID uuid, Rank rank);

}
