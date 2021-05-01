package me.appl3.devcontrol;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class DevControl extends JavaPlugin implements Listener {
	
	/*
	 * Made by Jedd Lupoy (0n3Appl3)
	 * 
	 * TODO: Make an announcement clock that repeats a message every 10 minutes.
	 */
	
	public void onEnable() {		
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7> &aDevControl has been Enabled!"));
		Bukkit.getPluginManager().registerEvents(this, this);
		
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			Bukkit.broadcastMessage(formatMessage("&c&m-----------------------------------------------------"));
			Bukkit.broadcastMessage(formatMessage("&c&lYOU ARE CURRENTLY BETA TESTING VERSION 4.0-b1"));
			Bukkit.broadcastMessage(formatMessage(""));
			Bukkit.broadcastMessage(formatMessage("&cBugs and glitches are expected.\nType &7&l/feedback &cto fill in a Google form.\nThank you for helping beta test the new server update!"));
			Bukkit.broadcastMessage(formatMessage("&c&m-----------------------------------------------------"));
		}, 0, 12000);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String exactLabel, String[] args) {
		Player player = (Player) sender;
		String label = cmd.getLabel();
		
		if (label.equalsIgnoreCase("feedback")) {
			sendMessage(player, "&f&lSend your feedback here&7:\n&6&nhttps://forms.gle/cWwuQPHiYRQ8wjAm9");
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
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		sendMessage(player, "&c&m-----------------------------------------------------");
		sendMessage(player, "&c&lYOU ARE CURRENTLY BETA TESTING VERSION 4.0-b1");
		sendMessage(player, "");
		sendMessage(player, "&cBugs and glitches are expected.\nType &7&l/feedback &cto fill in a Google form.\nThank you for helping beta test the new server update!");
		sendMessage(player, "&c&m-----------------------------------------------------");
		
	}
}
