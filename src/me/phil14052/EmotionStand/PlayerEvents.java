package me.phil14052.EmotionStand;

import me.phil14052.EmotionStand.Emotions.DabEmotion;
import me.phil14052.EmotionStand.Emotions.Emotion;
import me.phil14052.EmotionStand.Emotions.HelloEmotion;
import me.phil14052.EmotionStand.Emotions.SitEmotion;
import me.phil14052.EmotionStand.Emotions.WoawEmotion;

import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractAtEntityEvent;
import org.bukkit.inventory.meta.ItemMeta;

public class PlayerEvents implements Listener{

	private EmotionStand plugin = EmotionStand.getInstance();

	@EventHandler
	public void onArmorStandInteractEvent(PlayerInteractAtEntityEvent e){
		if(e.getRightClicked() instanceof ArmorStand){
			ArmorStand as = (ArmorStand) e.getRightClicked();
			for(Emotion es : plugin.getPlayers().values()){
				if(as.equals(es.getArmorStand())){
					e.setCancelled(true);
					return;
				}
			}
		}
	}
	
	@EventHandler
	public void onInvInteractEvent(InventoryClickEvent e){
		if(!(e.getWhoClicked() instanceof Player)) return;
		if(e.getCurrentItem() == null || e.getCurrentItem().getType() == Material.AIR) return;
		if(!e.getCurrentItem().hasItemMeta()) return;
		if(!e.getCurrentItem().getItemMeta().hasDisplayName()) return;
		ItemMeta im = e.getCurrentItem().getItemMeta();
		Player p = (Player) e.getWhoClicked();
		if(!e.getInventory().getTitle().equals("§c§lChoose an emotion:")) return;
		e.setCancelled(true);
		if(im.getDisplayName().equals("Hello")){
			p.closeInventory();
			HelloEmotion emotion = new HelloEmotion(p);
			emotion.start();
			
		}else if(im.getDisplayName().equals("Sit")){
			p.closeInventory();
			SitEmotion emotion = new SitEmotion(p);
			emotion.start();
		}else if(im.getDisplayName().equals("Woaw")){
			p.closeInventory();
			WoawEmotion emotion = new WoawEmotion(p);
			emotion.start();
		}else if(im.getDisplayName().equals("Dab")){
			p.closeInventory();
			DabEmotion emotion = new DabEmotion(p);
			emotion.start();
		}
	}
	
	
	
}
