package me.wtfblaze.commands;

import me.wtfblaze.ArgyleEconomy;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EcoCommand extends CommandBase {
    public EcoCommand(ArgyleEconomy instance) {
        super(instance, "aeco", Arrays.asList("aeco", "aeconomy"));
        register(true);
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (args.length == 0) {
            commandSender.sendMessage(plugin.formatColors("&7=========&8[&cArgyle&4Economy&8]&7========="));
            commandSender.sendMessage(plugin.formatColors("&c/aeco &7- &fthis menu."));
            commandSender.sendMessage(plugin.formatColors("&c/abal &7- &fview yours or others balance."));
            commandSender.sendMessage(plugin.formatColors("&c/apay &7- &fpay another player"));
            if (commandSender.hasPermission("argyle_econ.set"))
                commandSender.sendMessage(plugin.formatColors("&c/aeco give &7- &fset a player's balance."));
            if (commandSender.hasPermission("argyle_econ.reset"))
                commandSender.sendMessage(plugin.formatColors("&c/aeco reset &7- &freset a player's balance back to 0."));
            commandSender.sendMessage(plugin.formatColors("&7================================"));
        } else {
            switch (args[0].toLowerCase()) {
                case "reset" -> {
                    String targetName = args[1];
                    Player target = plugin.getServer().getPlayer(targetName);
                    if (target == null) {
                        commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fuser not found!"));
                        return true;
                    }
                    plugin.getDatabase().setBalance(target, 0);
                    commandSender.sendMessage(plugin.formatColors(String.format("&8[&cArgyle&4Economy&8] &7>> &fSuccessfully reset %s's balance to &a$0!", target.getName())));
                    return true;
                }
                case "set" -> {
                    String targetName = args[1];
                    Player target = plugin.getServer().getPlayer(targetName);
                    if (target == null) {
                        commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fuser not found!"));
                        return true;
                    }
                    try {
                        int amount = Integer.parseInt(args[2]);
                        plugin.getDatabase().setBalance(target, amount);
                        commandSender.sendMessage(plugin.formatColors(String.format("&8[&cArgyle&4Economy&8] &7>> &fSuccessfully set %s's balance to &a$%s!", target.getName(), amount)));
                    } catch (NumberFormatException ex) {
                        commandSender.sendMessage(plugin.formatColors("&8[&cArgyle&4Economy&8] &7>> &fthat is not a valid amount!"));
                        return true;
                    }
                    return true;
                }
                default -> {
                    commandSender.sendMessage(plugin.formatColors("&7=========&8[&cArgyle&4Economy&8]&7========="));
                    commandSender.sendMessage(plugin.formatColors("&c/aeco &7- &fthis menu."));
                    commandSender.sendMessage(plugin.formatColors("&c/abal &7- &fview yours or others balance."));
                    commandSender.sendMessage(plugin.formatColors("&c/apay &7- &fpay another player"));
                    if (commandSender.hasPermission("argyle_econ.set"))
                        commandSender.sendMessage(plugin.formatColors("&c/aeco give &7- &fset a player's balance."));
                    if (commandSender.hasPermission("argyle_econ.reset"))
                        commandSender.sendMessage(plugin.formatColors("&c/aeco reset &7- &freset a player's balance back to 0."));
                    commandSender.sendMessage(plugin.formatColors("&7================================"));
                }
            }
        }
        return true;
    }

    @SuppressWarnings("NullableProblems")
    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String s, String[] args)
    {
        List<String> results = new ArrayList<>();
        switch (args.length) {
            case 1 -> {
                if (commandSender.hasPermission("argyle_econ.set"))
                    results.add("set");
                if (commandSender.hasPermission("argyle_econ.reset"))
                    results.add("reset");
            }
            case 2 -> {
                for (Player player : plugin.getServer().getOnlinePlayers())
                    results.add(player.getName());
            }
            case 3 -> {
                if (args[1].equalsIgnoreCase("set")) {
                    results.add("1");
                    results.add("10");
                    results.add("100");
                    results.add("1000");
                    results.add("10000");
                    results.add("100000");
                }
            }
        }
        return results.size() == 0 ? null : results;
    }
}
