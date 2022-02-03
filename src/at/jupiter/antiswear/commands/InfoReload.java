package at.jupiter.antiswear.commands;

import at.jupiter.antiswear.AntiSwear;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoReload implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length > 0 && strings[0].equalsIgnoreCase("reload")) {
            if (commandSender.hasPermission("antiswear.reload")) {
                AntiSwear.plugin.reloadConfig();
                AntiSwear.config = AntiSwear.plugin.getConfig();
                AntiSwear.loadConfig();

                commandSender.sendMessage(AntiSwear.prefix + " §aConfig successfully reloaded!");
            } else commandSender.sendMessage(AntiSwear.prefix + " §cInsufficient permissions!");
        } else {
            commandSender.sendMessage(AntiSwear.prefix + " §av§b" + AntiSwear.plugin.getDescription().getVersion());
        }
        return false;
    }
}
