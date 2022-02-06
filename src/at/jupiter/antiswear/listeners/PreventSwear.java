package at.jupiter.antiswear.listeners;

import at.jupiter.antiswear.AntiSwear;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class PreventSwear implements Listener {
    @EventHandler
    public void onAsyncPlayerChat(AsyncPlayerChatEvent e) {
        Player p = e.getPlayer();

        final String[] message = {e.getMessage()};

        if (!e.getPlayer().hasPermission("antiswear.bypass")) {
            AntiSwear.ignoredChars.forEach(x -> {
                message[0] = message[0].replaceAll(Pattern.quote(x), "");
            });

            List<String> swearWords = AntiSwear.forbiddenWords.stream().filter(x -> message[0].toLowerCase().contains(x.toLowerCase())).collect(Collectors.toList());

            if (swearWords.size() > 0) {
                e.setCancelled(true);

                p.sendMessage(AntiSwear.prefix + " " + AntiSwear.blockedMessage);
                p.playSound(p.getLocation(), Sound.ENTITY_ENDER_DRAGON_GROWL, SoundCategory.MASTER, 1, 1);

                notifyMods(p, e.getMessage());
                punishCommand(p);
            }
        }
    }

    void notifyMods(Player swearer, String message) {
        Bukkit.getOnlinePlayers().stream().filter(x -> x.hasPermission("antiswear.notify")).collect(Collectors.toList()).forEach(x -> {
            x.sendMessage(AntiSwear.prefix + " §aThe player §6" + swearer.getName() + " §atried to send the following message:\n§e" + message);
        });
    }

    void punishCommand(Player swearer) {
        new BukkitRunnable() {
            @Override
            public void run() {
                String punishCommand = AntiSwear.config.getString("punishCommand");

                if (punishCommand != null) {
                    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), punishCommand.replace("%player%", swearer.getName()));
                }
            }
        }.runTask(AntiSwear.plugin);
    }
}
