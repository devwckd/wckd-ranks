package co.wckd.ranks.adapter;

import co.wckd.boilerplate.adapter.ObjectAdapter;
import co.wckd.ranks.entity.section.Permissions;

import java.util.Arrays;

public class StringArrayToPermissionsAdapter implements ObjectAdapter<String[], Permissions> {

    @Override
    public Permissions adapt(String[] strings) {
        Permissions permissions = new Permissions();

        if (strings.length != 0)
            permissions.setSection(Arrays.asList(strings));

        return permissions;
    }
}
