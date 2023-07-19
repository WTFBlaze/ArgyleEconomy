package me.wtfblaze.shoptguiplus;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import me.wtfblaze.ArgyleEconomy;
import net.brcdev.shopgui.ShopGuiPlusApi;
import net.brcdev.shopgui.event.ShopGUIPlusPostEnableEvent;

public class ShopGUIPlusHook implements Listener
{
	private ArgyleEconomy plugin;
	
	public ShopGUIPlusHook(ArgyleEconomy instance) {
		this.plugin = instance;
	}
	
	@EventHandler
	public void onShopGUIPlusPostEnable(ShopGUIPlusPostEnableEvent event)
	{
		ShopGuiPlusApi.registerEconomyProvider(plugin.getProvider());
		plugin.getLogger().info("Registered ArgyleEconomy provider in ShopGUI+!");
	}
}
