package co.wckd.vips.command;

import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class VipCommand {


    @Command(
            name = "wickedvips",
            aliases = {"wckdvips", "wv"},
            permission = "wickedvips.admin"
    )
    public void onWVCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDVIPS §8- §fGeneral Help.",
                " ",
                " §8➟ §e/wv rank <options> §8- §fVIP ranks section.",
                " §8➟ §e/wv key <options> §8- §fVIP keys section.",
                " "
        });

    }

}
