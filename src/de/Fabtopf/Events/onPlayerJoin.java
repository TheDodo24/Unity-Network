package de.Fabtopf.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPreLoginEvent;
import org.bukkit.event.player.PlayerPreLoginEvent.Result;

import de.Fabtopf.Utilities.Wartung;
import net.md_5.bungee.api.ChatColor;

@SuppressWarnings("deprecation")
public class onPlayerJoin implements Listener {
	public Wartung pl;

	public onPlayerJoin() {
		this.pl = Wartung.getInstance();
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}

	@SuppressWarnings("unchecked")
	@EventHandler
	public void playerJoin(PlayerPreLoginEvent e) {

		if (Wartung.general.getBoolean("Wartung")) {
			List<String> list = new ArrayList<String>();

			try {
				list = (List<String>) Wartung.wartung.getObject("players");
			} catch (Exception ex) {
				Bukkit.getLogger().warning("[Unity Wartung] In der Whitelist-Datei wurde ein Fehler gefunden!");
			}

			if (!(list.contains(e.getName()))) {
				e.disallow(Result.KICK_WHITELIST,
						ChatColor.translateAlternateColorCodes('&', Wartung.general.getString("KickMessage")));
			}
		}

	}
}
