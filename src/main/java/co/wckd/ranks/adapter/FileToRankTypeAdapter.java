package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.rank.RankType;
import co.wckd.ranks.entity.rank.section.Commands;
import co.wckd.ranks.entity.rank.section.Items;
import co.wckd.ranks.entity.rank.section.Messages;
import co.wckd.ranks.entity.rank.section.Title;
import co.wckd.ranks.util.Strings;
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
        String prettyName = Strings.colorize(configuration.getString("pretty_name", identifier));
        Commands commands = ADAPTER.adapt(configuration, MemorySection.class, Commands.class);
        Messages messages = ADAPTER.adapt(configuration, MemorySection.class, Messages.class);
        Items items = ADAPTER.adapt(configuration, MemorySection.class, Items.class);
        Title title = ADAPTER.adapt(configuration, MemorySection.class, Title.class);

        return RankType
                .builder()
                .identifier(identifier)
                .prettyName(prettyName)
                .commands(commands)
                .messages(messages)
                .items(items)
                .title(title)
                .build();

    }

}
