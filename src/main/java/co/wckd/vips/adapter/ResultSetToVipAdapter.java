package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipTypeCache;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipType;

import java.sql.ResultSet;

public class ResultSetToVipAdapter implements ObjectAdapter<ResultSet, Vip> {

    private static final VipsPlugin PLUGIN = VipsPlugin.getInstance();
    private static final VipTypeCache VIP_TYPE_CACHE = PLUGIN.getVipTypeLifecycle().getVipTypeCache();

    @Override
    public Vip adapt(ResultSet resultSet) {
        try {

            VipType type = VIP_TYPE_CACHE.find(resultSet.getString("type"));
            long time = resultSet.getLong("time");

            return Vip
                    .builder()
                    .type(type)
                    .time(time)
                    .build();

        } catch (Exception exception) {
            return null;
        }
    }

}
