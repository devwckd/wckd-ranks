package co.wckd.ranks.util;

import lombok.RequiredArgsConstructor;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.List;

@RequiredArgsConstructor
public class Lang {

    private final FileConfiguration lang;

    public String get(String path) {
        List<String> stringList = lang.getStringList(path);
        stringList.replaceAll(s -> s = ChatColor.translateAlternateColorCodes('&', s));
        return String.join("\n", stringList);
    }


}
