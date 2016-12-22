package de.Fabtopf.Utilities;

import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import de.Fabtopf.API.ConfigManager;

public class Unity extends JavaPlugin {

	ConfigManager mysqlfile = new ConfigManager("plugins/Unity", "MySQL.yml");

	private static Unity instance;

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
		mysqlfile.addDefault("username", "root");
		mysqlfile.addDefault("password", "pass");
		mysqlfile.addDefault("database", "Unity");
		mysqlfile.addDefault("host", "localhost");
		mysqlfile.addDefault("port", 3306);
	}

	public void registerCommands() {

	}

	public void registerEvents() {

	}

	public static Plugin getInstance() {
		return instance;
	}
}
