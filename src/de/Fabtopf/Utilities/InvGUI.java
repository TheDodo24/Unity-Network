package de.Fabtopf.Utilities;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import de.Fabtopf.API.AnvilGUI;
import de.Fabtopf.API.AnvilGUI.AnvilClickEvent;

public class InvGUI {
	public static void Wartung(Player p) {
		Inventory i = Bukkit.createInventory(null, InventoryType.HOPPER, "§cUnity Wartung");

		ItemStack enable = new ItemStack(Material.WOOL, 1, (byte) 5);
		ItemMeta enableMeta = enable.getItemMeta();
		enableMeta.setDisplayName("§cWartung §aeinschalten");

		if (Wartung.general.getBoolean("Wartung")) {
			enableMeta.addEnchant(Enchantment.DIG_SPEED, 1, false);
		}

		enableMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		enable.setItemMeta(enableMeta);

		ItemStack disable = new ItemStack(Material.WOOL, 1, (byte) 14);
		ItemMeta disableMeta = disable.getItemMeta();
		disableMeta.setDisplayName("§cWartung §4ausschalten");

		if (!Wartung.general.getBoolean("Wartung")) {
			disableMeta.addEnchant(Enchantment.DIG_SPEED, 1, false);
		}

		disableMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		disable.setItemMeta(disableMeta);

		ItemStack player = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta playerMeta = (SkullMeta) player.getItemMeta();
		playerMeta.setOwner(p.getName());
		playerMeta.setDisplayName("§eSpieler");
		player.setItemMeta(playerMeta);

		i.setItem(0, enable);
		i.setItem(2, player);
		i.setItem(4, disable);

		p.openInventory(i);
	}

	@SuppressWarnings({ "unchecked", "deprecation" })
	public static void WartungPlayer(Player p) {
		Inventory i = Bukkit.createInventory(null, 54, "§cUnity Wartung §eSpielerliste");

		List<String> whitelisted = new ArrayList<String>();

		try {
			whitelisted = (List<String>) Wartung.wartung.getObject("players");
		} catch (Exception e) {
			Bukkit.getLogger().warning("[Unity Wartung] In der Whitelist-Datei wurde ein Fehler gefunden!");
		}

		SkullMeta skullMeta;
		ItemStack skull;
		for (int d = 0; d < (whitelisted.size()); d++) {

			OfflinePlayer player = Bukkit.getOfflinePlayer(whitelisted.get(d));
			String name = player.getName();

			skull = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
			skullMeta = (SkullMeta) skull.getItemMeta();
			skullMeta.setDisplayName(ChatColor.GREEN + name);
			skullMeta.setOwner(name);
			skull.setItemMeta(skullMeta);
			if (d <= 44) {
				i.setItem(d, skull);
			}
		}

		ItemStack close = new ItemStack(Material.BARRIER);
		ItemMeta closeMeta = close.getItemMeta();
		closeMeta.setDisplayName("§cClose");
		close.setItemMeta(closeMeta);

		ItemStack add = new ItemStack(Material.ANVIL);
		ItemMeta addMeta = add.getItemMeta();
		addMeta.setDisplayName("§eSpieler §ahinzufügen");
		add.setItemMeta(addMeta);

		ItemStack refresh = new ItemStack(Material.DIAMOND);
		ItemMeta refreshMeta = refresh.getItemMeta();
		refreshMeta.setDisplayName("§cAktualisieren");
		refresh.setItemMeta(refreshMeta);

		i.setItem(45, close);
		i.setItem(49, add);
		i.setItem(53, refresh);

		p.openInventory(i);
	}

	public static void WartungRemove(Player p, String target) {
		Inventory i = Bukkit.createInventory(null, 9, "§cUnity Wartung Remove");

		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (byte) 3);
		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		headMeta.setOwner(target);
		headMeta.setDisplayName("§4" + target + " entfernen");

		List<String> lore = new ArrayList<String>();
		lore.add("§eUm den Spieler");
		lore.add("§e" + target + " von der");
		lore.add("§eWhitelist zu entfernen");
		lore.add("§eklicke erneut!");

		headMeta.setLore(lore);
		head.setItemMeta(headMeta);

		ItemStack cancel = new ItemStack(Material.BARRIER);
		ItemMeta cancelMeta = cancel.getItemMeta();
		cancelMeta.setDisplayName("§cCancel");
		cancel.setItemMeta(cancelMeta);

		i.setItem(4, head);
		i.setItem(8, cancel);

		p.openInventory(i);
	}

	public static void WartungAdd(Player p) {
		AnvilGUI gui = new AnvilGUI(p, new AnvilGUI.AnvilClickEventHandler() {

			@SuppressWarnings("unchecked")
			@Override
			public void onAnvilClick(AnvilClickEvent event) {
				if (event.getSlot() == AnvilGUI.AnvilSlot.OUTPUT) {
					event.setWillClose(false);
					event.setWillDestroy(false);
					List<String> list = new ArrayList<String>();

					try {
						list = (List<String>) Wartung.wartung.getObject("players");
					} catch (Exception ex) {
						Bukkit.getLogger().warning("[Unity Wartung] In der Whitelist-Datei wurde ein Fehler gefunden!");
					}

					if (!list.contains(event.getName())) {
						list.add(event.getName());
						Wartung.wartung.set("players", list);

						p.sendMessage(Wartung.getPrefix() + "Der Spieler §e" + event.getName()
								+ " §cwurde zur Whitelist hinzugefügt!");
					} else {
						p.sendMessage(Wartung.getPrefix() + "Der Spieler §e" + event.getName()
								+ " §cist bereits auf der Whitelist!");
					}

					InvGUI.WartungPlayer(p);
				} else {
					event.setWillClose(false);
					event.setWillDestroy(false);
				}
			}
		});

		ItemStack paper = new ItemStack(Material.PAPER);
		ItemMeta paperMeta = paper.getItemMeta();
		paperMeta.setDisplayName("§cSpieler zum hinzufügen");
		paper.setItemMeta(paperMeta);

		gui.setSlot(AnvilGUI.AnvilSlot.INPUT_LEFT, paper);

		gui.open();
	}
}
