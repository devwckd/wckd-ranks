package co.wckd.vips.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.vips.VipsPlugin;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

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
        if (!vipTypeFolder.exists()) {
            vipTypeFolder.mkdirs();
            copyResource("_example.yml", new File(vipTypeFolder, "_example.yml"));
        }

    }

    private void copyResource(String name, File to) {
        try {
            if (!to.exists())
                to.createNewFile();

            InputStream in = plugin.getResource(name);
            OutputStream out = new FileOutputStream(to);
            byte[] buf = new byte[1024];

            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }

            out.close();
            in.close();
        } catch (Exception exception) {
            exception.printStackTrace();
            // TODO: log
        }
    }

}
