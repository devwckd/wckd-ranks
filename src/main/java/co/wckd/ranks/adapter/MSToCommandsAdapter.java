package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.entity.rank.section.Commands;
import org.bukkit.configuration.MemorySection;

public class MSToCommandsAdapter implements ObjectAdapter<MemorySection, Commands> {

    @Override
    public Commands adapt(MemorySection section) {
        Commands commands = new Commands();

        commands.setOnActivateSection(section.getStringList("on_activate.commands"));
        commands.setOnChangeToSection(section.getStringList("on_change_to.commands"));
        commands.setOnChangedFromSection(section.getStringList("on_changed_from.commands"));

        return commands;
    }
}
