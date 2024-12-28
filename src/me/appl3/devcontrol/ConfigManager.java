package me.appl3.devcontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ConfigManager {
	private final DevControl plugin = DevControl.getPlugin(DevControl.class);
	
	// File Configuration
	public FileConfiguration config;
	
	// File
	public File file;
	
	// File Name
	public String configFile = "config.yml";
	
	public void setup() {
		if (!plugin.getDataFolder().exists()) {
			plugin.getDataFolder().mkdir(); // Create plugin directory if not found.
		}
		
		file = new File(plugin.getDataFolder(), configFile);

		config = YamlConfiguration.loadConfiguration(file);

		checkFile(file, configFile);
	}
	
	public void checkFile(File fi, String fileName) {
		if (!fi.exists()) {
			try {
				fi.createNewFile();
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&aThe " + fileName + " file has been created!"));
				initConfig();
			} catch (IOException e) {
				Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
						"&cCould not create the " + fileName + " file!"));
			}
		}
	}

	// Initialising Configuration

	public void initConfig() {
		ArrayList<String> messages = new ArrayList<>();
		messages.add("&fNeed a bit of assistance? Visit the guides page &bnoxite.co.nz/guides &fto learn more.");
		messages.add("&fWant to chat on the go? Join the official Discord server &bdiscord.noxite.co.nz");
		messages.add("&fWant to stay up-to-date on news and updates? Follow us on Bluesky at &b@noxitenz.bsky.social");
		messages.add("&fWant to support the server in style? Visit the store page &bstore.noxite.co.nz &fto learn more.");

		getConfig().set("announcements.messages", messages);
		getConfig().set("announcements.messageIntervalMinutes", 10);
		getConfig().set("announcements.prefix", "&c&l(!)");
		getConfig().set("joinMessage.firstTime", "&7> &c&lWelcome, {{username}}!");
		getConfig().set("joinMessage.returning", "&7> &c&lWelcome back, {{username}}!");
		saveConfig();
	}
	
	// Getting Configuration
	
	public FileConfiguration getConfig() {
		return config;
	}
	
	// Saving Configuration
	
	public void saveConfig() {
		try {
			config.save(file);
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&aThe " + configFile + " file has been saved!"));
		} catch (IOException e) {
			Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
					"&cCould not save the " + configFile + " file!"));
		}
	}
	
	// Reloading Configuration
	
	public void reloadConfig() {
		config = YamlConfiguration.loadConfiguration(file);
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&',
				"&aThe " + configFile + " file has been reloaded!"));
	}

	// Modifying Configuration

	public String getAnnouncementPrefix() {
		return getConfig().getString("announcements.prefix");
	}

	public ArrayList<String> getAnnouncementMessages() {
		return (ArrayList<String>) getConfig().getStringList("announcements.messages");
	}

	public int getAnnouncementInterval() {
		return getConfig().getInt("announcements.messageIntervalMinutes");
	}

	public String getWelcomeBackMessage(String username) {
		String message = getConfig().getString("joinMessage.returning");
		if (message == null) {
			return "Welcome back, " + username + "!";
		}
        return message.replace("{{username}}", username);
	}

	public String getWelcomeMessage(String username) {
		String message = getConfig().getString("joinMessage.firstTime");
		if (message == null) {
			return "Welcome, " + username + "!";
		}
		return message.replace("{{username}}", username);
	}
}
