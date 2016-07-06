package me.phil14052.EmotionStand;

import java.util.HashMap;
import java.util.Map;

import me.phil14052.EmotionStand.Emotions.Emotion;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class EmotionStand extends JavaPlugin{

	private static EmotionStand plugin;
	private Map<Player, Emotion> players;
	@Override
	public void onEnable(){
		plugin = this;
		players = new HashMap<Player, Emotion>();
		registerCommands();
		registerEvents();
	}
		
	@Override
	public void onDisable(){
		for(Emotion es : players.values()){
			es.stop();
		}
		plugin = null;
		players = null;
	}
	public Map<Player, Emotion> getPlayers(){
		return players;
	}
	public static EmotionStand getInstance(){
		return plugin;
	}
	private void registerEvents() {
	    PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new PlayerEvents(), this);
	}
	
	private void registerCommands() {
		getCommand("emotions").setExecutor(new MainCommand());
	}
	
}
