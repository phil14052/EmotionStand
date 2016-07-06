package me.phil14052.EmotionStand.Emotions;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public interface Emotion{
	
	public void start();
	
	public void stop();
	
	public ArmorStand getArmorStand();
	
	public void setArmorStand(ArmorStand as);
	
	public boolean canPlayerMove();

	public void setPlayerMove(boolean mode);
	
	public Player getPlayer();
	
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e);
	
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent e);
	
	public EmotionType getType();
}
