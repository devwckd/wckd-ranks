package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.entity.section.*;
import org.bukkit.configuration.MemorySection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class FileToRankTypeAdapter implements ObjectAdapter<File, RankType> {

    private static final RanksPlugin PLUGIN = RanksPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public RankType adapt(File file) {

        if (file == null) return null;
        if (file.isDirectory()) return null;

        String fileName = file.getName();
        if (fileName.startsWith("_") || !fileName.endsWith(".yml")) return null;
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(file);

        String identifier = fileName.toLowerCase().substring(0, fileName.length() - 4);
        PrettyName prettyName = ADAPTER.adapt(configuration.getString("pretty_name"), String.class, PrettyName.class);
        Permissions permissions = ADAPTER.adapt(configuration.getStringList("permissions").toArray(new String[0]), String[].class, Permissions.class);
        Commands commands = ADAPTER.adapt(configuration.getStringList("commands").toArray(new String[0]), String[].class, Commands.class);
        Items items = ADAPTER.adapt(configuration.getConfigurationSection("items"), MemorySection.class, Items.class);
        Title title = ADAPTER.adapt(configuration.getConfigurationSection("title"), MemorySection.class, Title.class);

        return RankType
                .builder()
                .identifier(identifier)
                .prettyName(prettyName)
                .permissions(permissions)
                .commands(commands)
                .items(items)
                .title(title)
                .build();

    }

}
