package co.wckd.vips.command;

import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;

public class VipRankCommand {


    @Command(
            name = "wickedvips.rank"
    )
    public void onWVCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDVIPS §8- §fRanks Help.",
                " ",
                " §8➟ §e/wv rank give <player> <group> [duration] §8- §fGives a VIP rank to the player.",
                " §8➟ §e/wv rank remove <player> <group> [duration] §8- §fRemoves a VIP rank from the player.",
                " "
        });

    }

}
