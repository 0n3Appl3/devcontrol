package me.appl3.devcontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;

public class DevControl extends JavaPlugin implements Listener {
	
	/*
	 * Made by Jedd Lupoy (0n3Appl3)
	 */

	private ConfigManager config;
	private ArrayList<String> messages = new ArrayList<>();

	int msgIndex = 0;
	int announcementTask = 0;
	String announcementPrefix = "";
	long announcementInterval = 0;

	public void onEnable() {
		loadConfigManager();
		applyConfigChanges();

		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7> &aDevControl has been Enabled!"));
		Bukkit.getPluginManager().registerEvents(this, this);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String exactLabel, String[] args) {
		String label = cmd.getLabel();

		if (label.equalsIgnoreCase("devreload")) {
			if (!(sender instanceof ConsoleCommandSender)) {
				Player player = (Player) sender;
				if (!player.hasPermission("appl3.devreload")) {
					sendMessage(player, "&cYou do not have permission to use this!");
					return true;
				}
			}
			config.reloadConfig();
			applyConfigChanges();
		}
		return true;
	}
	
	// Converts ampersands into symbols used for color coding.
	public void sendMessage(CommandSender sender, String message) {
		sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public String formatMessage(String message) {
		return ChatColor.translateAlternateColorCodes('&', message);
	}

	public void loadConfigManager() {
		config = new ConfigManager();
		config.setup();
		config.reloadConfig();
	}

	public void applyConfigChanges() {
		announcementPrefix = config.getAnnouncementPrefix();
		announcementInterval = config.getAnnouncementInterval();
		messages = config.getAnnouncementMessages();
		msgIndex = 0;

		Bukkit.getServer().getScheduler().cancelTask(announcementTask);
		startAnnouncementTask();
	}

	public void startAnnouncementTask() {
		announcementTask = Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, () -> {
			Bukkit.broadcastMessage(formatMessage("\n" + announcementPrefix + " " + messages.get(msgIndex) + "\n "));

			msgIndex++;
			if (msgIndex == messages.size())
				msgIndex = 0;
		}, 0, 20 * (announcementInterval * 60L));
	}
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();

		if (player.hasPlayedBefore())
			sendMessage(player, config.getWelcomeBackMessage(player.getName()));
		else
			sendMessage(player, config.getWelcomeMessage(player.getName()));
	}
}
