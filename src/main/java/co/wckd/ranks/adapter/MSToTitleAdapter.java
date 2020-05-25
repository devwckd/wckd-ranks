package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.boilerplate.util.Strings;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.rank.section.Title;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;

import java.util.ArrayList;
import java.util.List;

public class MSToTitleAdapter implements ObjectAdapter<MemorySection, Title> {

    private static final RanksPlugin PLUGIN = RanksPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public Title adapt(MemorySection section) {

        Title title = new Title();

        title.setOnActivateSection(getTitle(section.getConfigurationSection("on_activate.title")));
        title.setOnChangeToSection(getTitle(section.getConfigurationSection("on_change_to.title")));
        title.setOnChangedFromSection(getTitle(section.getConfigurationSection("on_changed_from.title")));

        return title;
    }

    public List<String> getTitle(ConfigurationSection memorySection) {

        if (memorySection == null) return null;
        final String type = memorySection.getString("type");
        if (type == null || type.isEmpty()) return null;
        final String titleString = memorySection.getString("title", "");
        final String subtitleString = memorySection.getString("subtitle", "");

        return new ArrayList<String>() {{
            add(type);
            add(Strings.colorize(titleString));
            add(Strings.colorize(subtitleString));
        }};
    }

}
