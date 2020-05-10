package co.wckd.vips;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import co.wckd.vips.adapter.*;
import co.wckd.vips.entity.VipType;
import co.wckd.vips.entity.section.Items;
import co.wckd.vips.entity.section.Permissions;
import co.wckd.vips.entity.section.PrettyName;
import co.wckd.vips.entity.section.Title;
import co.wckd.vips.lifecycle.DatabaseLifecycle;
import co.wckd.vips.lifecycle.FileLifecycle;
import co.wckd.vips.lifecycle.VipTypeLifecycle;
import co.wckd.vips.listener.TrafficListener;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Getter
public class VipsPlugin extends BoilerplatePlugin {

    private final Adapter adapter = new AdapterImpl();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final VipTypeLifecycle vipTypeLifecycle = lifecycle(new VipTypeLifecycle(this), 1);
    private final DatabaseLifecycle databaseLifecycle = lifecycle(new DatabaseLifecycle(this), 2);

    @Override
    public void load() {
        adapter.registerAdapter(String.class, PrettyName.class, new StringToPrettyNameAdapter());
        adapter.registerAdapter(String[].class, Permissions.class, new StringArrayToPermissionsAdapter());
        adapter.registerAdapter(MemorySection.class, Items.class, new MemorySectionToItemsAdapter());
        adapter.registerAdapter(MemorySection.class, Title.class, new MemorySectionToTitleAdapter());
        adapter.registerAdapter(File.class, VipType.class, new FileToVipTypeAdapter());
    }

    @Override
    public void enable() {
        registerListeners();
    }

    @Override
    public void disable() {

    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new TrafficListener(this), this);
    }

    public void log(String string) {
        Bukkit.getConsoleSender().sendMessage("[" + getDescription().getName() + "] " + string);
    }

    public static VipsPlugin getInstance() {
        return getPlugin(VipsPlugin.class);
    }

}
