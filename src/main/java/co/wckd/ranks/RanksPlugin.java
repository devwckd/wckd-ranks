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
import co.wckd.ranks.entity.key.Key;
import co.wckd.ranks.entity.rank.Rank;
import co.wckd.ranks.entity.rank.RankPlayer;
import co.wckd.ranks.entity.rank.RankType;
import co.wckd.ranks.entity.rank.section.Commands;
import co.wckd.ranks.entity.rank.section.Items;
import co.wckd.ranks.entity.rank.section.Messages;
import co.wckd.ranks.entity.rank.section.Title;
import co.wckd.ranks.lifecycle.*;
import co.wckd.ranks.listener.TrafficListener;
import co.wckd.ranks.runnable.RankTimeCheckRunnable;
import co.wckd.ranks.util.TimeUtils;
import lombok.Getter;
import me.saiintbrisson.commands.CommandFrame;
import org.bukkit.Bukkit;
import org.bukkit.configuration.MemorySection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.PluginManager;
import org.bukkit.scheduler.BukkitScheduler;

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
    private final KeyLifecycle keyLifecycle = lifecycle(new KeyLifecycle(this), 4);

    @Override
    public void load() {
        adapter.registerAdapter(MemorySection.class, ItemStack.class, new CSToISAdapter());
        adapter.registerAdapter(MemorySection.class, Commands.class, new MSToCommandsAdapter());
        adapter.registerAdapter(MemorySection.class, Messages.class, new MSToMessagesAdapter());
        adapter.registerAdapter(MemorySection.class, Items.class, new MSToItemsAdapter());
        adapter.registerAdapter(MemorySection.class, Title.class, new MSToTitleAdapter());
        adapter.registerAdapter(File.class, RankType.class, new FileToRankTypeAdapter());
        adapter.registerAdapter(ResultSet.class, Rank.class, new RSToRankAdapter());
        adapter.registerAdapter(ResultSet.class, RankPlayer.class, new RSToRankPlayerAdapter());
        adapter.registerAdapter(ResultSet.class, Key.class, new RSToKeyAdapter());
    }

    @Override
    public void enable() {
        registerListeners();
        registerCommands();
        startTimers();
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

    private void startTimers() {
        BukkitScheduler scheduler = getServer().getScheduler();
        int delay = fileLifecycle.getConfiguration().getInt("config.vip_time_update_interval");
        scheduler.runTaskTimerAsynchronously(
                this,
                new RankTimeCheckRunnable(this, (long) delay * 1000),
                0L, delay * 20L);
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
