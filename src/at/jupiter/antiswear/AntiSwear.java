package at.jupiter.antiswear;

import org.bukkit.plugin.java.JavaPlugin;

public class AntiSwear extends JavaPlugin {
    public static JavaPlugin plugin;
    
    @Override
    public void onEnable() {
        plugin = this;

        getServer().getConsoleSender().sendMessage("§aORanks v" + getDescription().getVersion() + " has been successfully enabled!");
    }
}
