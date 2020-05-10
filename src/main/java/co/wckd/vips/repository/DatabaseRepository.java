package co.wckd.vips.repository;

import co.wckd.boilerplate.object.DAO;
import co.wckd.vips.entity.VipPlayer;

import java.util.UUID;

public abstract class DatabaseRepository implements DAO<UUID, VipPlayer> {
}
