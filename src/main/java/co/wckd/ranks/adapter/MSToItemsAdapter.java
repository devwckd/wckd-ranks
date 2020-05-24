package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.rank.section.Items;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.MemorySection;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MSToItemsAdapter implements ObjectAdapter<MemorySection, Items> {

    private static final RanksPlugin PLUGIN = RanksPlugin.getInstance();
    private static final Adapter ADAPTER = PLUGIN.getAdapter();

    @Override
    public Items adapt(MemorySection section) {

        Items items = new Items();

        items.setOnActivateSection(getItems(section.getConfigurationSection("on_activate.items")));
        items.setOnActivateSection(getItems(section.getConfigurationSection("on_activate.items")));
        items.setOnActivateSection(getItems(section.getConfigurationSection("on_activate.items")));

        return items;
    }

    private List<ItemStack> getItems(ConfigurationSection section) {
        if (section == null) return null;

        Set<String> keys = section.getKeys(false);
        if (keys == null) return null;

        List<ItemStack> itemStacks = new ArrayList<>();
        for (String key : keys) {
            ConfigurationSection configurationSection = section.getConfigurationSection(key);
            ItemStack stack = ADAPTER.adapt(configurationSection, MemorySection.class, ItemStack.class);
            if (stack == null) continue;
            itemStacks.add(stack);
        }
        return itemStacks;
    }

}
