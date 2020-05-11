package co.wckd.vips.lifecycle;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.cache.VipTypeCache;
import co.wckd.vips.entity.VipType;
import lombok.Getter;

import java.io.File;

@Getter
public class VipTypeLifecycle extends Lifecycle {

    private final VipsPlugin plugin;
    private final Adapter adapter;
    private final VipTypeCache vipTypeCache;

    private File vipTypeFolder;

    public VipTypeLifecycle(VipsPlugin plugin) {
        this.plugin = plugin;
        this.adapter = plugin.getAdapter();
        this.vipTypeCache = new VipTypeCache();
    }

    @Override
    public void enable() {

        vipTypeFolder = plugin.getFileLifecycle().getVipTypeFolder();

        plugin.log("Loading vip types...");
        loadVipTypes();
        plugin.log(vipTypeCache.getVipTypes().size() + " vip types loaded.");

    }

    private void loadVipTypes() {

        File[] files = vipTypeFolder.listFiles();

        for (File file : files) {
            VipType type = adapter.adapt(file, File.class, VipType.class);
            if (type != null) vipTypeCache.insert(type.getIdentifier(), type);
        }

    }

}
