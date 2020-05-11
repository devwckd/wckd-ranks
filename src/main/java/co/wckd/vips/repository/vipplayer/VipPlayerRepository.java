package co.wckd.vips.repository.vipplayer;

import co.wckd.boilerplate.object.DAO;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;

import java.util.UUID;

public interface VipPlayerRepository extends DAO<UUID, VipPlayer> {

    void deleteSingle(UUID uuid, Vip vip);

}
