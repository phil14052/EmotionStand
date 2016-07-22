package me.phil14052.EmotionStand.GUIs;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChooseEmotionGUI {

	private Player p;
	private Inventory inv;
	public ChooseEmotionGUI(Player p){
		this.p = p;
		inv = Bukkit.createInventory(null, 9, "§c§lChoose an emotion:");
		ItemStack is = new ItemStack(Material.APPLE);
		ItemMeta im = is.getItemMeta();
		im.setDisplayName("Hello");
		is.setItemMeta(im);
		inv.addItem(is);
		im.setDisplayName("Sit");
		is.setItemMeta(im);
		inv.addItem(is);
		im.setDisplayName("Woaw");
		is.setItemMeta(im);
		inv.addItem(is);
		im.setDisplayName("Dab");
		is.setItemMeta(im);
		inv.addItem(is);
	}
	
	public void open(){
		p.openInventory(inv);
	}
}
