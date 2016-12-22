package de.Fabtopf.Commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import de.Fabtopf.Utilities.InvGUI;
import de.Fabtopf.Utilities.Wartung;

public class FabCommandWartung implements CommandExecutor {

	Wartung pl;

	public FabCommandWartung(Wartung unity) {
		this.pl = unity;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (label.equalsIgnoreCase("wartung")) {
			if (sender instanceof Player) {
				Player p = (Player) sender;
				if (p.hasPermission("fab.wartung") || p.isOp()) {
					InvGUI.Wartung(p);
				} else {
					p.sendMessage(
							Wartung.getPrefix() + "Du hast keine Berechtigung für diesen Befehl! §e(fab.wartung)");
				}
				return true;
			} else {
				if (Wartung.general.getBoolean("Wartung")) {
					Wartung.general.set("Wartung", false);
					sender.sendMessage("Wartung ausgeschaltet");
					return true;
				} else {
					Wartung.general.set("Wartung", true);
					sender.sendMessage("Wartung ausgeschaltet");
					return true;
				}
			}
		}

		return false;
	}

}
