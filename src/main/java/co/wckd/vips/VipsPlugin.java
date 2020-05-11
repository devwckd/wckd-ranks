package co.wckd.vips;

import co.wckd.boilerplate.adapter.Adapter;
import co.wckd.boilerplate.adapter.AdapterImpl;
import co.wckd.boilerplate.adapter.CSToISAdapter;
import co.wckd.boilerplate.plugin.BoilerplatePlugin;
import co.wckd.vips.adapter.*;
import co.wckd.vips.command.VipCommand;
import co.wckd.vips.command.VipKeyCommand;
import co.wckd.vips.command.VipRankCommand;
import co.wckd.vips.entity.Vip;
import co.wckd.vips.entity.VipPlayer;
import co.wckd.vips.entity.VipType;
import co.wckd.vips.entity.section.*;
import co.wckd.vips.lifecycle.DatabaseLifecycle;
import co.wckd.vips.lifecycle.FileLifecycle;
import co.wckd.vips.lifecycle.VipPlayerLifecycle;
import co.wckd.vips.lifecycle.VipTypeLifecycle;
import co.wckd.vips.listener.TrafficListener;
import co.wckd.vips.util.TimeUtils;
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

@Getter
public class VipsPlugin extends BoilerplatePlugin {

    private final Adapter adapter = new AdapterImpl();
    private final ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final FileLifecycle fileLifecycle = lifecycle(new FileLifecycle(this), 0);
    private final DatabaseLifecycle databaseLifecycle = lifecycle(new DatabaseLifecycle(this), 1);
    private final VipTypeLifecycle vipTypeLifecycle = lifecycle(new VipTypeLifecycle(this), 2);
    private final VipPlayerLifecycle vipPlayerLifecycle = lifecycle(new VipPlayerLifecycle(this), 3);

    @Override
    public void load() {
        adapter.registerAdapter(MemorySection.class, ItemStack.class, new CSToISAdapter());
        adapter.registerAdapter(String.class, PrettyName.class, new StringToPrettyNameAdapter());
        adapter.registerAdapter(String[].class, Permissions.class, new StringArrayToPermissionsAdapter());
        adapter.registerAdapter(String[].class, Commands.class, new StringArrayToCommandsAdapter());
        adapter.registerAdapter(MemorySection.class, Items.class, new MemorySectionToItemsAdapter());
        adapter.registerAdapter(MemorySection.class, Title.class, new MemorySectionToTitleAdapter());
        adapter.registerAdapter(File.class, VipType.class, new FileToVipTypeAdapter());
        adapter.registerAdapter(ResultSet.class, Vip.class, new ResultSetToVipAdapter());
        adapter.registerAdapter(ResultSet.class, VipPlayer.class, new ResultSetToVipPlayerAdapter());
    }

    @Override
    public void enable() {
        registerListeners();
        registerCommands();
    }

    @Override
    public void disable() {
    }

    private void registerListeners() {
        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new TrafficListener(this), this);
    }

    private void registerCommands() {
        CommandFrame commandFrame = new CommandFrame(this, false);
        registerTypes(commandFrame);
        commandFrame.registerCommands(
                new VipCommand(),
                new VipKeyCommand(),
                new VipRankCommand(this)
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
        commandFrame.registerType(VipType.class, (argument) -> vipTypeLifecycle.getVipTypeCache().find(argument.toLowerCase()));
        commandFrame.registerType(Player.class, Bukkit::getPlayer);
    }

    public void log(String string) {
        Bukkit.getConsoleSender().sendMessage("[" + getDescription().getName() + "] " + string);
    }

    public static VipsPlugin getInstance() {
        return getPlugin(VipsPlugin.class);
    }

}
