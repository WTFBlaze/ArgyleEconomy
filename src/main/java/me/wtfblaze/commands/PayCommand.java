package me.wtfblaze.commands;

import me.wtfblaze.ArgyleEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.logging.Level;

public class PayCommand extends CommandBase {
    public PayCommand(ArgyleEconomy instance) {
        super(instance, "apay", List.of("apay"));
        register(true);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof Player player) {
            if (args.length == 0) {
                player.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fyou must specify who to pay!"));
                return true;
            }
            if (args.length == 1) {
                player.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fyou must specify an amount!"));
                return true;
            }
            String targetName = args[0];
            Player target = plugin.getServer().getPlayer(targetName);
            if (target == null) {
                commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fuser not found!"));
                return true;
            }
            try {
                int amount = Integer.parseInt(args[1]);
                double sendersBalance = plugin.getDatabase().getBalance(player);
                if (sendersBalance >= amount) {
                    sendersBalance = sendersBalance - amount;
                    plugin.getDatabase().setBalance(player, sendersBalance);
                    double targetsBalance = plugin.getDatabase().getBalance(target);
                    targetsBalance = targetsBalance + amount;
                    plugin.getDatabase().setBalance(target, targetsBalance);
                    player.sendMessage(plugin.formatColors(String.format("&8[&cArgyle&4Economy&8] &7>> &fyou sent %s &a$%s", target.getName(), amount)));
                } else {
                    player.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fyou don't have enough to send!"));
                }
            } catch (NumberFormatException ex) {
                plugin.getLogger().log(Level.WARNING, String.format("%s tried to pay %s with an invalid amount! (Value: %s)", player.getName(), target.getName(), args[1]));
                player.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fthat is not a valid amount!"));
                return true;
            }
        } else {
            commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fyou must be a player to use this command!"));
        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args) {
        return null;
    }
}
