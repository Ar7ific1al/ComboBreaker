package com.github.ar7ific1al.combobreaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class ChatListener implements Listener{
	
	Plugin plugin;
	
	public ChatListener(Plugin instance){
		plugin = instance;
		Plugin.console.info("[ComboBreaker] Player Chat Listener registered.");
	}
	
	String lastPlayer = null;
	String combo[] = new String[]{ "" };
	int comboCount = 0;
	
	@EventHandler
	public void playerChat(AsyncPlayerChatEvent event){
		if (!event.isCancelled()){
			File settings = new File(plugin.getDataFolder(), "settings.yml");
			FileConfiguration tmpfc = new YamlConfiguration();
			try {
				tmpfc.load(settings);
				if (tmpfc.getBoolean("Enabled")){			
					lastPlayer = event.getPlayer().getName();
					
					if (combo[0].equals("")){
						combo[0] = event.getMessage();
					}
					else{
						if (combo[0].equals(event.getMessage())){
							comboCount++;
						}
						else if (combo[0] != event.getMessage()){
							combo[0] = event.getMessage();
							if (comboCount >= 5){
								Bukkit.getServer().broadcastMessage(ChatColor.RED + "C-C-C-COMBO BREAKER!!");
								comboCount = 0;
							}
						}
					}
				}
			} catch (IOException | InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*
			Bukkit.getServer().broadcastMessage("Last Player: " + lastPlayer);
			Bukkit.getServer().broadcastMessage("Message: " + combo[0]);
			Bukkit.getServer().broadcastMessage("Combo Count: " + comboCount);
			*/
		}
	}
}
