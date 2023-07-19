package me.wtfblaze.commands;

import me.wtfblaze.ArgyleEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BalanceCommand extends CommandBase {

    public BalanceCommand(ArgyleEconomy instance) {
        super(instance, "abalance", Arrays.asList("abalance", "abal"));
        register(true);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            if (commandSender instanceof Player player) {
                double balance = plugin.getDatabase().getBalance(player);
                player.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fYour balance is &a$" + balance));
                return true;
            }
            commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fyou must specify a player to view their balance!"));
        } else {
            if (commandSender instanceof Player player && !player.hasPermission("argyle_econ.balance.others")) {
                double balance = plugin.getDatabase().getBalance(player);
                player.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fYour balance is &a$" + balance));
                return true;
            }
            String targetName = args[0];
            Player target = plugin.getServer().getPlayer(targetName);
            if (target == null) {
                commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fuser not found!"));
                return true;
            }
            double balance = plugin.getDatabase().getBalance(target);
            commandSender.sendMessage(plugin.formatColors(String.format("&8[&cArgyle&4Economy&8] &7>> &f%s's balance is &a$%s", target.getName(), balance)));
        }
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length != 1)
            return null;
        List<String> results = new ArrayList<>();
        for (Player player : plugin.getServer().getOnlinePlayers())
            results.add(player.getName());
        return results;
    }
}
