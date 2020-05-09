package co.wckd.vips.lifecycle;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipTypeCache;
import co.wckd.vips.entity.VipType;

import java.io.File;

public class VipTypeLifecycle extends Lifecycle {

    private final VipsPlugin plugin;
    private final Adapter adapter;
    private final File vipTypeFolder;
    private final VipTypeCache vipTypeCache;

    public VipTypeLifecycle(VipsPlugin plugin) {
        this.plugin = plugin;
        this.adapter = plugin.getAdapter();
        this.vipTypeFolder = plugin.getFileLifecycle().getVipTypeFolder();
        this.vipTypeCache = new VipTypeCache();
    }

    @Override
    public void enable() {
        loadVipTypes();
    }

    private void loadVipTypes() {

        File[] listFiles = vipTypeFolder.listFiles();

        if (listFiles == null) {
            // TODO: log error
            return;
        }

        for (File file : listFiles) {
            VipType type = adapter.adapt(file, VipType.class);
            if (type != null) vipTypeCache.insert(type.getIdentifier(), type);
        }

    }

}
