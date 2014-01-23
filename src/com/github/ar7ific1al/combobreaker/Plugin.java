package com.github.ar7ific1al.combobreaker;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Logger;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Plugin extends JavaPlugin{
	
	public static Logger console = Logger.getLogger("Minecraft");
	public String version;
	
	@Override
	public void onEnable(){
		PluginManager pm = Bukkit.getServer().getPluginManager();
		PluginDescriptionFile pdFile = this.getDescription();
		
		version = pdFile.getVersion();
		
		console.info("[ComboBreaker] ComboBreaker version " + version + " enabled.");
		
		if (!getDataFolder().exists()){
			getDataFolder().mkdir();
		}
		
		ChatListener chatlistener = new ChatListener(this);
		
		pm.registerEvents(chatlistener, this);
		
		File settings = new File(getDataFolder(), "settings.yml");
		if (!settings.exists()){
			try {
				settings.createNewFile();
				FileConfiguration tmpfc = new YamlConfiguration();
				tmpfc.load(settings);
				tmpfc.addDefault("Enabled", true);
				tmpfc.options().copyDefaults(true);
				tmpfc.save(settings);
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		}
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args){
		if (cmdLabel.equalsIgnoreCase("combobreaker")){
			if (args.length == 0){
				sender.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
				sender.sendMessage(ChatColor.RED + "ComboBreaker written by " + ChatColor.YELLOW + "Ar7ific1al" + ChatColor.RED + "! :3");
				sender.sendMessage(ChatColor.DARK_PURPLE + "" + ChatColor.MAGIC + "ABCDEFGHIJKLMNOPQRSTUVWXYZ");
			}
			else if (args.length == 1){
				if (args[0].equalsIgnoreCase("toggle")){
					if (sender.hasPermission("combobreaker.admin")){
						try {
							sender.sendMessage(toggleComboBreakers());
						} catch (IOException | InvalidConfigurationException e) {
							e.printStackTrace();
						}
					}
					else{
						sender.sendMessage(ChatColor.RED + "You don't have permission to use that command!");
					}
				}
			}
			return true;
		}
		return false;
	}
	
	public String toggleComboBreakers() throws FileNotFoundException, IOException, InvalidConfigurationException{
		String result = "";
		File settings = new File(getDataFolder(), "settings.yml");
		FileConfiguration tmpfc = new YamlConfiguration();
		tmpfc.load(settings);
		if (tmpfc.getBoolean("Enabled")){
			tmpfc.set("Enabled", false);
			result = ChatColor.RED + "[ComboBreaker] " + ChatColor.YELLOW + "Combo Breakers have been toggled off.";
		}
		else{
			tmpfc.set("Enabled", true);
			result = ChatColor.RED + "[ComboBreaker] " + ChatColor.YELLOW + "Combo Breakers have been toggled on.";
		}
		tmpfc.save(settings);
		return result;
	}
}
