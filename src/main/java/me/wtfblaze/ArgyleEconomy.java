package me.wtfblaze;

import me.wtfblaze.commands.BalanceCommand;
import me.wtfblaze.commands.EcoCommand;
import me.wtfblaze.commands.PayCommand;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;

public final class ArgyleEconomy extends JavaPlugin {

    private Database db;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdir();
        }
        db = new SQLite(this);
        db.load();
        ArgyleEconomyAPI.initialize(this);
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new Placeholders(this).register();
        else
            getLogger().log(Level.SEVERE, "PlaceholderAPI is null!");
        new BalanceCommand(this);
        new PayCommand(this);
        new EcoCommand(this);
    }

    public Database getDatabase() {
        return db;
    }

    public String formatColors(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
}
