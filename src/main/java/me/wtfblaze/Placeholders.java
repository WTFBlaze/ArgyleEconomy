package me.wtfblaze;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.text.DecimalFormat;

public class Placeholders extends PlaceholderExpansion {

    private final ArgyleEconomy plugin;

    public Placeholders(ArgyleEconomy instance) {
        plugin = instance;
    }

    @Override
    public @NotNull String getName() {
        return "ArgyleEconomy";
    }

    @Override
    public @NotNull String getAuthor() {
        return "WTFBlaze";
    }

    @Override
    public @NotNull String getIdentifier() {
        return "argyleecon";
    }

    @Override
    public @NotNull String getVersion() {
        return "1.0.0";
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @Nullable String onPlaceholderRequest(Player player, @NotNull String params) {
        switch (params.toLowerCase()) {
            case "balance" -> {
                return String.format("$%s", plugin.getDatabase().getBalance(player));
            }
            case "balance_exclude_trail" -> {
                DecimalFormat format = new DecimalFormat("0.#");
                return String.format("$%s", format.format(plugin.getDatabase().getBalance(player)));
            }
            case "balance_exclude_symbol" -> {
                return plugin.getDatabase().getBalance(player).toString();
            }
            case "balance_exclude_symbol_trail" -> {
                DecimalFormat format = new DecimalFormat("0.#");
                return format.format(plugin.getDatabase().getBalance(player));
            }
        }
        return null;
    }
}