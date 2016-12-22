package de.Fabtopf.MySQL;

import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.bukkit.Bukkit;

import de.Fabtopf.Messages.PluginMessages;
import de.Fabtopf.Utilities.Wartung;

public class MySQL {
	public static String username;
	public static String password;
	public static String database;
	public static String host;
	public static String port;
	public static boolean dev;

	public static java.sql.Connection con;

	public static void connect() {
		if (!(isConnected())) {
			try {
				con = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username,
						password);
			} catch (SQLException e) {
				if (dev) {
					e.printStackTrace();
				} else {
					Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
					Bukkit.getPluginManager().disablePlugin(Wartung.getInstance());
				}
			}
		}
	}

	public static void disconnect() {
		if (isConnected()) {
			try {
				con.close();
			} catch (SQLException e) {
				if (dev) {
					e.printStackTrace();
				} else {
					Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
				}
			}

		}
	}

	public static boolean isConnected() {
		return con != null;
	}

	public static void createTable() {
		if (isConnected()) {
			try {
				if (!MySQL.tableExists("Fab_Utilities")) {
					con.createStatement().executeUpdate(
							"CREATE TABLE IF NOT EXISTS Fab_Utilities (Type VARCHAR(64), Wert VARCHAR(100))");
					con.createStatement().executeUpdate("ALTER TABLE `Fab_Utilities` ADD UNIQUE (`Type`)");
				}
			} catch (SQLException e) {
				if (dev) {
					e.printStackTrace();
				} else {
					Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
					disconnect();
					connect();
				}
			}
		}
	}

	public static void update(String qry) {
		if (MySQL.isConnected()) {
			try {
				Statement st = con.createStatement();
				st.executeUpdate(qry);
				st.close();
			} catch (SQLException e) {
				if (dev) {
					connect();
					e.printStackTrace();
				} else {
					Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
				}
			}
		} else {
			Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
			disconnect();
			connect();
		}
	}

	public static ResultSet getResult(String qry) {
		if (MySQL.isConnected()) {
			try {
				return con.createStatement().executeQuery(qry);
			} catch (SQLException e) {
				if (dev) {
					connect();
					e.printStackTrace();
				} else {
					Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
				}
			}
		} else {
			Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
			disconnect();
			connect();
		}
		return null;
	}

	public static boolean tableExists(String table) {
		if (MySQL.isConnected()) {
			ResultSet rs = MySQL.getResult("SHOW TABLES LIKE '" + table + "'");
			int i = 0;
			try {
				while (rs.next()) {
					i++;
				}
				if (i > 0) {
					return true;
				}
			} catch (SQLException e) {
				if (dev) {
					e.printStackTrace();
				} else {
					Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
				}
			}
		} else {
			Bukkit.getLogger().severe(PluginMessages.MySQLConFail);
			disconnect();
			connect();
		}
		return false;
	}
}
