package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.entity.section.Messages;
import org.bukkit.configuration.MemorySection;

public class MSToMessagesAdapter implements ObjectAdapter<MemorySection, Messages> {

    @Override
    public Messages adapt(MemorySection section) {
        Messages messages = new Messages();

        messages.setOnActivateSection(section.getStringList("on_activate.commands"));
        messages.setOnChangeToSection(section.getStringList("on_change_to.commands"));
        messages.setOnChangedFromSection(section.getStringList("on_changed_from.commands"));

        return messages;
    }
}
