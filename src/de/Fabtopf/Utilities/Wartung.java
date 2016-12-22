package de.Fabtopf.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.plugin.java.JavaPlugin;

import de.Fabtopf.API.ConfigManager;
import de.Fabtopf.Commands.FabCommandWartung;
import de.Fabtopf.Events.onInventoryClick;
import de.Fabtopf.Events.onPlayerJoin;
import net.md_5.bungee.api.ChatColor;

public class Wartung extends JavaPlugin {

	public static ConfigManager general = new ConfigManager("plugins/Unity/Wartung", "general.yml");
	public static ConfigManager wartung = new ConfigManager("plugins/Unity/Wartung", "whitelist.yml");

	private static Wartung instance;
	private static String prefix;

	@Override
	public void onEnable() {

		instance = this;

		registerConfig();
		registerCommands();
		registerEvents();
	}

	@Override
	public void onDisable() {

	}

	public void registerConfig() {
		general.addDefault("MySQL", false);
		general.addDefault("Wartung", false);
		general.addDefault("Prefix", "&8[&cWartung&8] &c");
		general.addDefault("KickMessage",
				"&e&lDer Unity Minecraft-Server befindet sich\nmomentan in Wartungsarbeiten.\n\n&cWir bitten um Verständnis.");

		List<String> whitelist = new ArrayList<String>();
		whitelist.add("Fabtopf");
		wartung.addDefault("players", whitelist);

		prefix = ChatColor.translateAlternateColorCodes('&', general.getString("Prefix"));
	}

	public void registerCommands() {
		this.getCommand("wartung").setExecutor(new FabCommandWartung(this));
	}

	public void registerEvents() {
		new onInventoryClick();
		new onPlayerJoin();
	}

	public static Wartung getInstance() {
		return instance;
	}

	public static String getPrefix() {
		return prefix;
	}
}
