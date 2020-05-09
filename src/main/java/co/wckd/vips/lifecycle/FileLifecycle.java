package co.wckd.vips.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.vips.VipsPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;

@Getter
public class FileLifecycle extends Lifecycle {

    private final VipsPlugin plugin;
    private File vipTypeFolder;
    private FileConfiguration configuration;

    public FileLifecycle(VipsPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public void enable() {
        loadFiles();
    }

    private void loadFiles() {

        File dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists())
            dataFolder.mkdirs();

        plugin.saveDefaultConfig();
        configuration = plugin.getConfig();

        vipTypeFolder = new File(dataFolder, "/vips/");
        if (!vipTypeFolder.exists())
            vipTypeFolder.mkdirs();

    }

}
