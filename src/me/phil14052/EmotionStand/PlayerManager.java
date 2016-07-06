package me.phil14052.EmotionStand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerManager {

	private Map<Player, ArrayList<ItemStack>> saves;
	private static PlayerManager instance = null;
	
	public PlayerManager(){
		setSaves(new HashMap<Player, ArrayList<ItemStack>>());
	}
	
	public static PlayerManager getInstance(){
		if(instance == null) instance = new PlayerManager();
		return instance;
	}

	/**
	 * @return  the saves
	 */
	public Map<Player, ArrayList<ItemStack>> getSaves() {
		return saves;
	}

	/**
	 * @param saves the saves to set
	 */
	public void setSaves(Map<Player, ArrayList<ItemStack>> saves) {
		this.saves = saves;
	}
	
	public void savePlayer(Player p){
		ArrayList<ItemStack> list = new ArrayList<ItemStack>();
		list.add(0, p.getItemInHand());
		if(p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getType() != Material.AIR){
			 list.add(1, p.getInventory().getHelmet());
		}else{
			list.add(1, null);
		}
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() != Material.AIR){
			list.add(2,p.getInventory().getChestplate());
		}else{
			list.add(2, null);
		}
		if(p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getType() != Material.AIR){
			list.add(3, p.getInventory().getLeggings());
		}else{
			list.add(3, null);
		}
		if(p.getInventory().getBoots() != null && p.getInventory().getBoots().getType() != Material.AIR){
			list.add(4, p.getInventory().getBoots());
		}else{
			list.add(4, null);
		}
		this.getSaves().put(p, list);
	}
	
	public ArrayList<ItemStack> getPlayer(Player p){
		return this.getSaves().get(p);
	}
	
	public void restoreInv(Player p){
		ArrayList<ItemStack> l = this.getPlayer(p);
		if(l.get(0) != null && l.get(0).getType() != Material.AIR) p.setItemInHand(l.get(0));
		if(l.get(1) != null && l.get(1).getType() != Material.AIR) p.getInventory().setHelmet(l.get(1));
		if(l.get(2) != null && l.get(2).getType() != Material.AIR) p.getInventory().setChestplate(l.get(2));
		if(l.get(3) != null && l.get(3).getType() != Material.AIR) p.getInventory().setLeggings(l.get(3));
		if(l.get(4) != null && l.get(4).getType() != Material.AIR) p.getInventory().setBoots(l.get(4));
	}
	
	
}
