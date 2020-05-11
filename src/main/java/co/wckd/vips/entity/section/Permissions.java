package co.wckd.vips.entity.section;

import co.wckd.vips.VipsPlugin;
import co.wckd.vips.entity.VipType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissibleBase;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;

import java.util.List;

public class Permissions extends VipSection<List<String>> {

    @Override
    public void apply(Player player, VipType type) {

        if (!isPresent()) return;

        PermissibleBase permissibleBase = new PermissibleBase(player);
        for (String permission : getSection()) {
            player.getEffectivePermissions().add(
                    new PermissionAttachmentInfo(
                            permissibleBase,
                            permission,
                            new PermissionAttachment(VipsPlugin.getInstance(), permissibleBase),
                            true
                    )
            );
        }

    }

}
