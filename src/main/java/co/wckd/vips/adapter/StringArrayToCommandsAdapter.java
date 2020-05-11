package co.wckd.vips.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.vips.entity.section.Commands;

import java.util.Arrays;

public class StringArrayToCommandsAdapter implements ObjectAdapter<String[], Commands> {

    @Override
    public Commands adapt(String[] strings) {
        Commands commands = new Commands();

        if (strings.length != 0)
            commands.setSection(Arrays.asList(strings));

        return commands;
    }
}
