package co.wckd.vips.entity.section;

import co.wckd.vips.VipsPlugin;
import co.wckd.vips.entity.VipType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

public class Permissions extends VipSection<List<String>> {

    @Override
    public void apply(Player player, VipType type) {

        if (!isPresent()) return;

        PermissionAttachment permissionAttachment = player.addAttachment(VipsPlugin.getInstance());
        for (String permission : getSection()) {
            permissionAttachment.setPermission(permission, true);
        }

    }

}
