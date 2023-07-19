package me.wtfblaze.commands;

import me.wtfblaze.ArgyleEconomy;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.logging.Level;

public abstract class CommandBase implements CommandExecutor, TabCompleter {
    public String commandName;
    public List<String> overrideAliases;
    public ArgyleEconomy plugin;
    private static boolean checkedEssentials = false;
    private static boolean essentialsLoaded;

    public CommandBase(ArgyleEconomy instance, String cmdName, List<String> oAliases) {
        commandName = cmdName;
        plugin = instance;
        overrideAliases = oAliases;
    }

    public void register(boolean useTabCompleter) {
        // only have to check once for if essentials is loaded!
        if (!checkedEssentials) {
            Plugin essentialsPlugin = plugin.getServer().getPluginManager().getPlugin("Essentials");
            essentialsLoaded = essentialsPlugin != null;
            checkedEssentials = true;
        }

        PluginCommand cmd = plugin.getServer().getPluginCommand(commandName);
        if (cmd != null) {
            // if essentials is loaded set aliases to the override aliases. Otherwise use default aliases in plugin.yml
            if (essentialsLoaded)
                cmd.setAliases(overrideAliases);
            cmd.setExecutor(this);
            if (useTabCompleter)
                cmd.setTabCompleter(this);
        } else {
            plugin.getLogger().log(Level.SEVERE, String.format("Failed to register command: %s!", commandName));
        }
    }
}
