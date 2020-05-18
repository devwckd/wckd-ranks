package co.wckd.ranks.entity.section;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.RankType;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.List;

public class Permissions extends VipSection<List<String>> {

    @Override
    public void apply(Player player, RankType type) {

        if (!isPresent()) return;

        PermissionAttachment permissionAttachment = player.addAttachment(RanksPlugin.getInstance());
        for (String permission : getSection()) {
            permissionAttachment.setPermission(permission, true);
        }

    }

}
