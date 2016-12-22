package de.Fabtopf.API;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class ConfigManager {

	private String configPath;
	private String configFile;

	public ConfigManager(String configFilePath, String configFileName) {
		this.configFile = configFileName;
		this.configPath = configFilePath;

		standartConfigInput();
	}

	public File getFile() {
		return new File(this.configPath, this.configFile);
	}

	public FileConfiguration getFileConfiguration() {
		return YamlConfiguration.loadConfiguration(getFile());
	}

	public void standartConfigInput() {
		FileConfiguration cfg = getFileConfiguration();
		cfg.options().copyHeader(true);
		cfg.options().header("File created by Unity-Utilities");
		save(cfg);
	}

	public boolean set(String path, Object object) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) != null) {
			cfg.set(path, object);
			save(cfg);
			return true;
		} else {
			return false;
		}
	}

	public boolean del(String path) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) != null) {
			cfg.set(path, null);
			save(cfg);
			return true;
		} else {
			return false;
		}
	}

	public boolean addDefault(String path, Object object) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) == null) {
			cfg.options().copyDefaults(true);
			cfg.addDefault(path, object);
			save(cfg);
			return true;
		} else {
			return false;
		}
	}

	public Object getObject(String path) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) != null) {
			return cfg.get(path);
		}
		save(cfg);
		return null;
	}

	public int getInt(String path) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) != null) {
			return cfg.getInt(path);
		}
		save(cfg);
		return 0;
	}

	public String getString(String path) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) != null) {
			return cfg.getString(path);
		}
		save(cfg);
		return null;
	}

	public boolean getBoolean(String path) {
		FileConfiguration cfg = getFileConfiguration();
		if (cfg.get(path) != null) {
			return cfg.getBoolean(path);
		}
		save(cfg);
		return false;
	}

	public void save(FileConfiguration cfg) {
		try {
			cfg.save(getFile());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
