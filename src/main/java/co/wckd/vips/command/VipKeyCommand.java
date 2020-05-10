package co.wckd.vips.command;

import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class VipKeyCommand {


    @Command(
            name = "wickedvips.key"
    )
    public void onWVCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDVIPS §8- §fKeys Help.",
                " ",
                " §8➟ §e/wv key generate <group> [duration] §8- §fGenerates a KEY with the designer VIP rank.",
                " §8➟ §e/wv key delete <key|id> §8- §fDeletes a KEY.",
                " "
        });

    }

}
