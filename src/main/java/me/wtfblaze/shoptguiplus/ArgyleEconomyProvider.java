package me.wtfblaze.shoptguiplus;

import org.bukkit.entity.Player;

import me.wtfblaze.ArgyleEconomy;
import net.brcdev.shopgui.provider.economy.EconomyProvider;

public class ArgyleEconomyProvider extends EconomyProvider
{
	private ArgyleEconomy plugin;
	
	public ArgyleEconomyProvider(ArgyleEconomy instance)
	{
		super();
		this.plugin = instance;
	}

	@Override
	public String getName() { return "ArgyleEconomy"; }

	@Override
	public double getBalance(Player player) {
		return plugin.getDatabase().getBalance(player);
	}

	@Override
	public void deposit(Player player, double amount) {
		plugin.getDatabase().setBalance(player, getBalance(player) + amount);
	}

	@Override
	public void withdraw(Player player, double amount) {
		plugin.getDatabase().setBalance(player, getBalance(player) - amount);
	}

}
