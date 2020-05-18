package co.wckd.ranks;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.adapter.CSToISAdapter;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import co.wckd.ranks.adapter.*;
import co.wckd.ranks.command.ChangeRankCommand;
import co.wckd.ranks.command.RankTimeCommand;
import co.wckd.ranks.command.administrative.WRCommand;
import co.wckd.ranks.command.administrative.WRKeyCommand;
import co.wckd.ranks.command.administrative.WRRankCommand;
import co.wckd.ranks.entity.Rank;
import co.wckd.ranks.entity.RankPlayer;
import co.wckd.ranks.entity.RankType;
import co.wckd.ranks.entity.section.*;
import co.wckd.ranks.lifecycle.DatabaseLifecycle;
import co.wckd.ranks.lifecycle.FileLifecycle;
import co.wckd.ranks.lifecycle.RankPlayerLifecycle;
import co.wckd.ranks.lifecycle.RankTypeLifecycle;
import co.wckd.ranks.listener.TrafficListener;
import co.wckd.ranks.util.TimeUtils;
import lombok.Getter;
import me.saiintbrisson.commands.CommandFrame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;

import java.io.File;
import java.sql.ResultSet;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

@Getter
public class RanksPlugin extends BoilerplatePlugin {

    private final Adapter adapter = new AdapterImpl();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final DatabaseLifecycle databaseLifecycle = lifecycle(new DatabaseLifecycle(this), 1);
    private final RankTypeLifecycle rankTypeLifecycle = lifecycle(new RankTypeLifecycle(this), 2);
    private final RankPlayerLifecycle rankPlayerLifecycle = lifecycle(new RankPlayerLifecycle(this), 3);

    @Override
    public void load() {
        adapter.registerAdapter(MemorySection.class, ItemStack.class, new CSToISAdapter());
        adapter.registerAdapter(String.class, PrettyName.class, new StringToPrettyNameAdapter());
        adapter.registerAdapter(String[].class, Permissions.class, new StringArrayToPermissionsAdapter());
        adapter.registerAdapter(String[].class, Commands.class, new StringArrayToCommandsAdapter());
        adapter.registerAdapter(MemorySection.class, Items.class, new MemorySectionToItemsAdapter());
        adapter.registerAdapter(MemorySection.class, Title.class, new MemorySectionToTitleAdapter());
        adapter.registerAdapter(File.class, RankType.class, new FileToRankTypeAdapter());
        adapter.registerAdapter(ResultSet.class, Rank.class, new ResultSetToRankAdapter());
        adapter.registerAdapter(ResultSet.class, RankPlayer.class, new ResultSetToRankPlayerAdapter());
    }

    @Override
    public void enable() {
        registerListeners();
        registerCommands();
    }

    @Override
    public void disable() {
        try {
            executorService.awaitTermination(10, TimeUnit.SECONDS);
        } catch (Exception exception) {
            // TODO: log
        }
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new TrafficListener(this), this);
    }

    private void registerCommands() {
        CommandFrame commandFrame = new CommandFrame(this, false);
        registerTypes(commandFrame);
        commandFrame.registerCommands(
                new WRCommand(),
                new WRKeyCommand(),
                new WRRankCommand(this),
                new RankTimeCommand(this),
                new RankTimeCommand(this),
                new ChangeRankCommand(this)
        );
    }

    private void registerTypes(CommandFrame commandFrame) {
        commandFrame.registerType(String.class, String::valueOf);
        commandFrame.registerType(Character.class, (argument) -> argument.charAt(0));
        commandFrame.registerType(Integer.class, Integer::valueOf);
        commandFrame.registerType(Double.class, Double::valueOf);
        commandFrame.registerType(Long.class, TimeUtils::millisFromString);
        commandFrame.registerType(Boolean.class, Boolean::valueOf);
        commandFrame.registerType(Byte.class, Byte::valueOf);
        commandFrame.registerType(Character.TYPE, (argument) -> argument.charAt(0));
        commandFrame.registerType(Integer.TYPE, Integer::parseInt);
        commandFrame.registerType(Double.TYPE, Double::parseDouble);
        commandFrame.registerType(Long.TYPE, Long::parseLong);
        commandFrame.registerType(Boolean.TYPE, Boolean::parseBoolean);
        commandFrame.registerType(Byte.TYPE, Byte::parseByte);
        commandFrame.registerType(RankType.class, (argument) -> rankTypeLifecycle.getRankTypeCache().find(argument.toLowerCase()));
        commandFrame.registerType(Player.class, Bukkit::getPlayer);
    }

    public static RanksPlugin getInstance() {
        return getPlugin(RanksPlugin.class);
    }

}
