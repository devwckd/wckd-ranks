package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.vips.VipsPlugin;
import co.wckd.vips.entity.section.Items;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MemorySectionToItemsAdapter implements ObjectAdapter<MemorySection, Items> {

    private static final VipsPlugin PLUGIN = VipsPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public Items adapt(MemorySection section) {

        if (section == null) return new Items();

        Set<String> keys = section.getKeys(false);
        if (keys.isEmpty()) return new Items();

        List<ItemStack> itemStacks = new ArrayList<>();
        for (String key : keys) {

            ConfigurationSection configurationSection = section.getConfigurationSection(key);
            ItemStack stack = ADAPTER.adapt(configurationSection, MemorySection.class, ItemStack.class);
            if (stack == null) continue;
            itemStacks.add(stack);

        }

        Items items = new Items();

        if (!itemStacks.isEmpty())
            items.setSection(itemStacks);

        return items;
    }

}
