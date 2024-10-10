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

import java.util.ArrayList;

public class DevControl extends JavaPlugin implements Listener {
	
	/*
	 * Made by Jedd Lupoy (0n3Appl3)
	 * 
	 * TODO: Make an announcement clock that repeats a message every 10 minutes.
	 */

	ArrayList<String> messages = new ArrayList<>();

	int msgIndex = 0;
	int minuteInternal = 10;

	public void onEnable() {		
		Bukkit.getServer().getConsoleSender().sendMessage(ChatColor.translateAlternateColorCodes('&', "&7> &aDevControl has been Enabled!"));
		Bukkit.getPluginManager().registerEvents(this, this);

		messages.add("&fNeed a bit of assistance? Visit the guides page &bnoxite.co.nz/guides &fto learn more.");
		messages.add("&fWant to chat on the go? Join the official Discord server &bdiscord.noxite.co.nz");
		messages.add("&fWant to stay up-to-date on news and updates? Follow us on Twitter at &b@noxite_nz");
		messages.add("&fWant to support the server in style? Visit the store page &bstore.noxite.co.nz &fto learn more.");

		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
			//Bukkit.broadcastMessage(formatMessage("&7&m-----------------------------------------------------"));
			//Bukkit.broadcastMessage((formatMessage(" \n&c|||||||||| &c&lCURRENTLY BETA TESTING VERSION 4.1-B1. &c||||||||||\n")));
			Bukkit.broadcastMessage(formatMessage("\n&c&l(!) " + messages.get(msgIndex) + "\n "));
			//Bukkit.broadcastMessage(formatMessage("&7&m-----------------------------------------------------"));

			msgIndex++;
			if (msgIndex == messages.size())
				msgIndex = 0;
		}, 0, 20 * (minuteInternal * 60));
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String exactLabel, String[] args) {
		Player player = (Player) sender;
		String label = cmd.getLabel();
		
//		if (label.equalsIgnoreCase("feedback")) {
//			sendMessage(player, "&f&lSend your feedback here&7:\n&6&nhttps://forms.gle/cWwuQPHiYRQ8wjAm9");
//		}
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

		if (player.hasPlayedBefore())
			sendMessage(player, "&7> &c&lWelcome back, " + player.getName() + "!");
		else
			sendMessage(player, "&7> &c&lWelcome, " + player.getName() + "!");
	}
}
