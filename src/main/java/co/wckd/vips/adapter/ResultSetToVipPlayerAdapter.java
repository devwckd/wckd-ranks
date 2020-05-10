package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;

import java.sql.ResultSet;

public class ResultSetToVipPlayerAdapter implements ObjectAdapter<ResultSet, VipPlayer> {

    private static final VipsPlugin PLUGIN = VipsPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public VipPlayer adapt(ResultSet resultSet) {
        try {

            VipPlayer vipPlayer = new VipPlayer();

            while (resultSet.next()) {
                Vip vip = ADAPTER.adapt(resultSet, Vip.class);
                if (vip != null) vipPlayer.addVip(vip.getType().getIdentifier(), vip);
            }

            return vipPlayer;

        } catch (Exception exception) {
            return null;
        }
    }

}
