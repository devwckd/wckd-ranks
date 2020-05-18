package co.wckd.ranks.command;

import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class WRCommand {


    @Command(
            name = "wickedranks",
            aliases = {"wckdranks", "wr"},
            permission = "wickedranks.admin"
    )
    public void onWRCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDRANKS §8- §fGeneral Help.",
                " ",
                " §8➟ §e/wr rank <options> §8- §fVIP ranks section.",
                " §8➟ §e/wr key <options> §8- §fVIP keys section.",
                " "
        });

    }

}
