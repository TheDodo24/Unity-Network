package de.Fabtopf.Events;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.Fabtopf.Utilities.InvGUI;
import de.Fabtopf.Utilities.Wartung;
import net.md_5.bungee.api.ChatColor;

public class onInventoryClick implements Listener {

	public Wartung pl;

	public onInventoryClick() {
		this.pl = Wartung.getInstance();
		pl.getServer().getPluginManager().registerEvents(this, pl);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@EventHandler
	public void inventoryClick(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();

		if ((ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Unity Wartung"))) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR
					|| !e.getCurrentItem().hasItemMeta()) {
				return;
			}

			switch (e.getCurrentItem().getType()) {
			case WOOL:
				ItemMeta meta = e.getCurrentItem().getItemMeta();
				if (ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Wartung einschalten")) {
					if (Wartung.general.getBoolean("Wartung")) {
						p.sendMessage(Wartung.getPrefix() + "Der Wartungsmodus ist bereits aktiviert!");
					} else {
						Wartung.general.set("Wartung", true);
						p.sendMessage(Wartung.getPrefix() + "Der Wartungsmodus wurde §aaktiviert§c!");
						InvGUI.Wartung(p);
					}
				}
				if (ChatColor.stripColor(meta.getDisplayName()).equalsIgnoreCase("Wartung ausschalten")) {
					if (!Wartung.general.getBoolean("Wartung")) {
						p.sendMessage(Wartung.getPrefix() + "Der Wartungsmodus ist bereits deaktiviert!");
					} else {
						Wartung.general.set("Wartung", false);
						p.sendMessage(Wartung.getPrefix() + "Der Wartungsmodus wurde §4deaktiviert§c!");
						InvGUI.Wartung(p);
					}
				}
				break;
			case SKULL_ITEM:
				InvGUI.WartungPlayer(p);
				break;
			default:
				break;
			}
		}

		if ((ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Unity Wartung Spielerliste"))) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR
					|| !e.getCurrentItem().hasItemMeta()) {
				return;
			}

			switch (e.getCurrentItem().getType()) {
			case SKULL_ITEM:
				SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
				String owner = meta.getOwner();
				String name = Bukkit.getOfflinePlayer(owner).getName();
				InvGUI.WartungRemove(p, name);
				break;
			case BARRIER:
				p.closeInventory();
				break;
			case ANVIL:
				InvGUI.WartungAdd(p);
				break;
			default:
				break;
			}

		}

		if ((ChatColor.stripColor(e.getInventory().getName()).equalsIgnoreCase("Unity Wartung Remove"))) {
			e.setCancelled(true);

			if (e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR
					|| !e.getCurrentItem().hasItemMeta()) {
				return;
			}

			switch (e.getCurrentItem().getType()) {
			case SKULL_ITEM:
				SkullMeta meta = (SkullMeta) e.getCurrentItem().getItemMeta();
				String owner = meta.getOwner();

				List<String> list = new ArrayList<String>();

				try {
					list = (List<String>) Wartung.wartung.getObject("players");
				} catch (Exception ex) {
					Bukkit.getLogger().warning("[Unity Wartung] In der Whitelist-Datei wurde ein Fehler gefunden!");
				}

				list.remove(owner);
				Wartung.wartung.set("players", list);
				InvGUI.WartungPlayer(p);
				break;
			default:
				break;
			}

		}
	}
}
