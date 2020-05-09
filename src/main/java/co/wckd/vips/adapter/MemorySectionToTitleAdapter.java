package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.boilerplate.util.Strings;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.entity.section.Title;
import org.bukkit.configuration.MemorySection;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MemorySectionToTitleAdapter implements ObjectAdapter<MemorySection, Title> {

    private static final VipsPlugin PLUGIN = VipsPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public Title adapt(MemorySection section) {

        if (section == null) return new Title();

        Set<String> keys = section.getKeys(false);
        if (keys.isEmpty()) return new Title();


        final Title title = new Title();

        final String type = section.getString("type");
        if (type == null || type.isEmpty()) return title;

        final String titleString = section.getString("title", "");
        final String subtitleString = section.getString("subtitle", "");

        List<String> titles = new ArrayList<String>() {{
            add(type);
            add(Strings.colorize(titleString));
            add(Strings.colorize(subtitleString));
        }};

        if (!titles.isEmpty())
            title.setSection(titles);

        return title;
    }

}
