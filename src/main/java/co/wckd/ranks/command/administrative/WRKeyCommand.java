package co.wckd.ranks.command.administrative;

import co.wckd.ranks.RanksPlugin;
import co.wckd.ranks.entity.key.Key;
import co.wckd.ranks.entity.rank.RankType;
import co.wckd.ranks.repository.key.KeyRepository;
import co.wckd.ranks.util.Lang;
import co.wckd.ranks.util.Strings;
import co.wckd.ranks.util.TimeUtils;
import me.saiintbrisson.commands.Execution;
import me.saiintbrisson.commands.annotations.Command;
import me.saiintbrisson.commands.argument.Argument;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.concurrent.ExecutorService;

public class WRKeyCommand {

    private final RanksPlugin plugin;
    private final KeyRepository keyRepository;
    private final ExecutorService executorService;
    private final Lang lang;

    public WRKeyCommand(RanksPlugin plugin) {
        this.plugin = plugin;
        this.keyRepository = plugin.getKeyLifecycle().getKeyRepository();
        this.executorService = plugin.getExecutorService();
        this.lang = plugin.getFileLifecycle().getLang();
    }

    @Command(
            name = "wickedranks.key"
    )
    public void onWRKeyCommand(Execution execution) {

        execution.sendMessage(new String[]{
                " ",
                " §6§lWICKEDRANKS §8- §fKeys Help.",
                " ",
                " §8➟ §e/wr key generate <group> [duration] §8- §fGenerates a KEY with the designer VIP rank.",
                " §8➟ §e/wr key delete <key|id> §8- §fDeletes a KEY.",
                " "
        });

    }

    @Command(
            name = "wickedranks.key.generate"
    )
    public void onWRKeyGenerateCommand(
            Execution execution,
            RankType type,
            @Argument(nullable = true) String timeString
    ) {

        Long time = TimeUtils.millisFromString(timeString);
        if (time == null) {
            execution.sendMessage(" §6§lWICKEDRANKS §8➟ §cIncorrect time string.");
            return;
        }

        execution.sendMessage(" §6§lWICKEDRANKS §8➟ §aGenerating the key...");

        executorService.execute(() -> {

            String keyString = Strings.generateKey();
            Key key = keyRepository.find(keyString);

            while (key != null) {
                keyString = Strings.generateKey();
                key = keyRepository.find(keyString);
            }

            Key generatedKey = Key.builder()
                    .key(keyString)
                    .time(time)
                    .type(type)
                    .build();

            keyRepository.insert(generatedKey.getKey(), generatedKey);

            execution.sendMessage(" §6§lWICKEDRANKS §8➟ §e" + type.getPrettyName() + " rank's " +
                    lang.formatTime(time) +
                    " §fkey generated.");


            TextComponent keyComponent = new TextComponent(keyString);
            keyComponent.setClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, keyString));

            TextComponent textComponent = new TextComponent(" §6§lWICKEDRANKS §8➟ §fKey: §e");
            textComponent.addExtra(keyComponent);

            execution.getPlayer().spigot().sendMessage(textComponent);

        });


    }

}
