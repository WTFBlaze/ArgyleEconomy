package me.wtfblaze;

import org.bukkit.OfflinePlayer;

public class ArgyleEconomyAPI
{
    private static ArgyleEconomy plugin;

    public static double getBalance(OfflinePlayer offlinePlayer) {
        return plugin.getDatabase().getBalance(offlinePlayer);
    }

    public static void setBalance(OfflinePlayer offlinePlayer, double newBalance) {
        plugin.getDatabase().setBalance(offlinePlayer, newBalance);
    }

    static void initialize(ArgyleEconomy instance) {
        plugin = instance;
    }
}
