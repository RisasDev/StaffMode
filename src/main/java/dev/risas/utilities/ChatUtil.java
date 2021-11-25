package dev.risas.utilities;

import lombok.experimental.UtilityClass;
import org.bukkit.ChatColor;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Risas
 * Project: StaffMode
 * Date: 23-11-2021 - 2:01
 * Twitter: @RisasDev
 * GitHub: https://github.com/RisasDev
 */

@UtilityClass
public class ChatUtil {

    public String translate(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public List<String> translate(List<String> list) {
        return list.stream().map(ChatUtil::translate).collect(Collectors.toList());
    }
}
