package co.wckd.ranks.command;

import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class RankKeyCommand {


    @Command(
            name = "wickedranks.key"
    )
    public void onWVCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDRANKS §8- §fKeys Help.",
                " ",
                " §8➟ §e/wr key generate <group> [duration] §8- §fGenerates a KEY with the designer VIP rank.",
                " §8➟ §e/wr key delete <key|id> §8- §fDeletes a KEY.",
                " "
        });

    }

}
