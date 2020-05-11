package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;

import java.sql.ResultSet;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class ResultSetToVipPlayerAdapter implements ObjectAdapter<ResultSet, VipPlayer> {

    private static final VipsPlugin PLUGIN = VipsPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public VipPlayer adapt(ResultSet resultSet) {
        try {

            Map<String, Vip> vips = new ConcurrentHashMap<>();
            Vip active = null;

            String uuid = null;
            while (resultSet.next()) {

                Vip vip = ADAPTER.adapt(resultSet, ResultSet.class, Vip.class);
                if (vip == null) continue;
                vips.put(vip.getType().getIdentifier(), vip);

                if (resultSet.getBoolean("is_active") && active == null)
                    active = vip;

                uuid = resultSet.getString("uuid");
            }

            if (vips.isEmpty() || uuid == null) return null;

            VipPlayer vipPlayer = new VipPlayer(UUID.fromString(uuid));
            vipPlayer.addVips(vips);
            vipPlayer.setActive(active);
            return vipPlayer;

        } catch (Exception exception) {
            return null;
        }
    }

}
