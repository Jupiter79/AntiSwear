package at.jupiter.antiswear;

import at.jupiter.antiswear.commands.InfoReload;
import at.jupiter.antiswear.listeners.PreventSwear;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

public class AntiSwear extends JavaPlugin {
    public static JavaPlugin plugin;
    public static FileConfiguration config;

    public static String prefix = "§c§lAntiSwear §8|";

    public static String blockedMessage;
    public static List<String> ignoredChars = new ArrayList<>();
    public static List<String> forbiddenWords = new ArrayList<>();

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getConsoleSender().sendMessage("§aORanks v" + getDescription().getVersion() + " has been successfully enabled!");

        getCommand("antiswear").setExecutor(new InfoReload());

        loadConfig();

        loadListeners();
    }

    void loadListeners() {
        getServer().getPluginManager().registerEvents(new PreventSwear(), plugin);
    }

    public static void loadConfig() {
        forbiddenWords = new ArrayList<>();

        config = plugin.getConfig();
        plugin.saveDefaultConfig();

        blockedMessage = config.getString("blockedMessage");
        ignoredChars = config.getStringList("ignoredChars");

        List<String> preForbiddenWords = config.getStringList("words");

        // Removing duplicates
        List<String> addedWords = new ArrayList<>();

        preForbiddenWords.forEach(x -> {
            if (!addedWords.contains(x)) {
                forbiddenWords.add(x);
                addedWords.add(x);
            }
        });
    }
}
