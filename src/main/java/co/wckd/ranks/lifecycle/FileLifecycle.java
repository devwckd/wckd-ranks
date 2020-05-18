package co.wckd.ranks.lifecycle;

import co.wckd.boilerplate.lifecycle.Lifecycle;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.util.Lang;
import co.wckd.ranks.util.Logs;
import lombok.Getter;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

@Getter
public class FileLifecycle extends Lifecycle {

    private final RanksPlugin plugin;
    private File vipTypeFolder;
    private File langFolder;
    private FileConfiguration configuration;
    private Lang lang;

    public FileLifecycle(RanksPlugin plugin) {
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

        langFolder = new File(dataFolder, "/lang/");
        if (!langFolder.exists()) {
            langFolder.mkdirs();
            copyResource("en_us.yml", new File(langFolder, "en_us.yml"));
            copyResource("pt_br.yml", new File(langFolder, "pt_br.yml"));
        }
        loadLang();

    }

    private void loadLang() {

        String langString = configuration.getString("config.lang", "en_US");
        File langFile = new File(langFolder, langString.toLowerCase() + ".yml");
        if (!langFile.exists()) {

            langFile = new File(langFolder, "en_us.yml");
            if (!langFile.exists())
                copyResource("en_us.yml", langFile);

            Logs.consoleLog("Language file not found, returning to default (en_US).");

        }

        YamlConfiguration langConfiguration = YamlConfiguration.loadConfiguration(langFile);
        lang = new Lang(langConfiguration);

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
            Logs.consoleLog("Failed to create file " + to.getName());
        }
    }

}
