package co.wckd.vips.command;

import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class RankCommand {


    @Command(
            name = "wickedranks",
            aliases = {"wckdranks", "wr"},
            permission = "wickedranks.admin"
    )
    public void onWVCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKERANKS §8- §fGeneral Help.",
                " ",
                " §8➟ §e/wr rank <options> §8- §fVIP ranks section.",
                " §8➟ §e/wr key <options> §8- §fVIP keys section.",
                " "
        });

    }

}
