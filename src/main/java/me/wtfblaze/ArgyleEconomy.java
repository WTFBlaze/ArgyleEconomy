package me.wtfblaze;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import me.wtfblaze.commands.BalanceCommand;
import me.wtfblaze.commands.EcoCommand;
import me.wtfblaze.commands.PayCommand;
import me.wtfblaze.shoptguiplus.ArgyleEconomyProvider;
import me.wtfblaze.shoptguiplus.ShopGUIPlusHook;
import net.md_5.bungee.api.ChatColor;

public final class ArgyleEconomy extends JavaPlugin {

    private Database db;
    private ArgyleEconomyProvider provider;
    private ShopGUIPlusHook hook;

    @Override
    public void onEnable() {
        if (!getDataFolder().exists()) {
            //noinspection ResultOfMethodCallIgnored
            getDataFolder().mkdir();
        }
        db = new SQLite(this);
        db.load();
        ArgyleEconomyAPI.initialize(this);
        this.provider = new ArgyleEconomyProvider(this);
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null)
            new Placeholders(this).register();
        else
            getLogger().log(Level.SEVERE, "PlaceholderAPI is null!");
        new BalanceCommand(this);
        new PayCommand(this);
        new EcoCommand(this);
        
        shopGUIPlusHook();
    }

    public Database getDatabase() {
        return db;
    }

    public String formatColors(String msg) {
        return ChatColor.translateAlternateColorCodes('&', msg);
    }
    
    public ArgyleEconomyProvider getProvider() { return provider; }
    
    private void shopGUIPlusHook()
    {
    	if(Bukkit.getPluginManager().getPlugin("ShopGUIPlus") != null)
    	{
    		this.hook = new ShopGUIPlusHook(this);
    		Bukkit.getPluginManager().registerEvents(hook, this);
    		this.getLogger().info("ShopGUI+ integration successful!");
    	}
    	else this.getLogger().warning("ShopGUI+ not found, integration skipped!");
    }
}
