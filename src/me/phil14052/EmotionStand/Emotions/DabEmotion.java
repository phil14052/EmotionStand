package me.phil14052.EmotionStand.Emotions;

import me.phil14052.EmotionStand.EmotionStand;
import me.phil14052.EmotionStand.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.util.EulerAngle;

public class DabEmotion implements Emotion,Listener{

	private ArmorStand as;
	private BukkitTask bt;
	private EmotionStand plugin;
	private Player p;
	private boolean canMove = true;
	private boolean running = false;
	private PlayerManager pm;
	
	
	public DabEmotion(Player p){
		plugin = EmotionStand.getInstance();
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.p = p;
		this.canMove = true;
		pm = PlayerManager.getInstance();
		if(plugin.getPlayers().containsKey(p)){
			plugin.getPlayers().get(p).stop();
			plugin.getPlayers().remove(p);
		}
		plugin.getPlayers().put(p, this);
	}
	
	@Override
	public void start() {
		if(!p.isOnline()) return;
		running = true;
		as = (ArmorStand) p.getLocation().getWorld().spawnEntity(p.getLocation(), EntityType.ARMOR_STAND);
		as.setBasePlate(false);
		as.setArms(true);
		as.setMaxHealth(500D);
		as.setGravity(false);
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		SkullMeta sm = (SkullMeta) head.getItemMeta();
		sm.setOwner(p.getName());
		head.setItemMeta(sm);
		as.setHelmet(head);
		as.setSmall(true);
		as.setCustomName(" ");
		as.setCustomNameVisible(true);
		ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE, 1);
		ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS, 1);
		ItemStack boots = new ItemStack(Material.LEATHER_BOOTS, 1);
		as.setChestplate(chestplate);
		as.setLeggings(leggings);
		as.setBoots(boots);
		as.setItemInHand(p.getItemInHand());
		if(p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getType() != Material.AIR){
			as.setChestplate(p.getInventory().getChestplate());
		}
		if(p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getType() != Material.AIR){
			as.setLeggings(p.getInventory().getLeggings());
			
		}
		if(p.getInventory().getBoots() != null && p.getInventory().getBoots().getType() != Material.AIR){
			as.setBoots(p.getInventory().getBoots());	
		}
		pm.savePlayer(p);
		ItemStack air = new ItemStack(Material.AIR);
		p.setItemInHand(air);
		p.getInventory().setHelmet(air);
		p.getInventory().setChestplate(air);
		p.getInventory().setLeggings(air);
		p.getInventory().setBoots(air);
		as.setCanPickupItems(false);
		
		bt = new BukkitRunnable(){
			int frame = 0;
			@Override
			public void run() {
				as.setFireTicks(0);
				as.setFallDistance(0F);
				as.setMaxHealth(50D);
				as.setHealth(50D);
				if(!p.isOnline()){
					stop();
				}
				if(as.isDead()){
					stop();
					return;
				}
				as.setCustomName("Dab!");
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 255, 5, false, false));
				as.teleport(p);
				double e = 0.007550782857; //1% of the rotation (2,71828182845904523536028747135266249/360)
				if(frame > 10 && frame < 15){
					EulerAngle pose = as.getRightArmPose();
					pose = pose.subtract((e*38)*2, 0D,(e*14.7)*2);
					as.setRightArmPose(pose);
					EulerAngle pose2 = as.getLeftArmPose();
					pose2 = pose2.subtract((e*10)*2, 0D,0D);
					pose2 = pose2.add(0D,(e*10)*2,(e*10)*2);
					as.setLeftArmPose(pose2);
					EulerAngle pose3 = as.getHeadPose();
					pose3 = pose3.add((e*10)*2, 0D,0D);
					pose3 = pose3.subtract(0D,(e*10)*2,0D);
					as.setHeadPose(pose3);
				}else if(frame > 40 && frame < 50){
					EulerAngle pose = as.getRightArmPose();
					pose = pose.add(e*38, 0D,e*14.7);
					as.setRightArmPose(pose);
					EulerAngle pose2 = as.getLeftArmPose();
					pose2 = pose2.add(e*10, 0D,0D);
					pose2 = pose2.subtract(0D,e*10,e*10);
					as.setLeftArmPose(pose2);
					EulerAngle pose3 = as.getHeadPose();
					pose3 = pose3.subtract(e*10, 0D,0D);
					pose3 = pose3.add(0D,e*10,0D);
					as.setHeadPose(pose3);
					
				}else if(frame > 60){
					stop();
				}
				frame++;
				
			}
			
		}.runTaskTimer(plugin, 2, 1);
	}

	@Override
	public void stop() {
		running = false;
		if(bt != null){
			bt.cancel();
		}
		
		pm.restoreInv(p);
		
		if(!as.isDead() && as != null){
			as.remove();
		}
		if(p.isOnline() && p.hasPotionEffect(PotionEffectType.INVISIBILITY)){
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
		plugin.getPlayers().remove(p);
	}

	@Override
	public ArmorStand getArmorStand() {
		return as;
	}

	@Override
	public void setArmorStand(ArmorStand as) {
		this.as = as;
	}

	@Override
	public boolean canPlayerMove() {
		return this.canMove;
	}

	@Override
	public void setPlayerMove(boolean mode) {
		this.canMove = mode;
	}

	@Override
	@EventHandler
	public void onMoveEvent(PlayerMoveEvent e) {
		if(running && !canPlayerMove() && e.getPlayer().equals(this.getPlayer())){
			double x = e.getFrom().getBlockX();
			double y = e.getFrom().getBlockY();
			double z = e.getFrom().getBlockZ();
			double X = e.getTo().getBlockX();
			double Y = e.getTo().getBlockY();
			double Z = e.getTo().getBlockZ();
			if(X != x || Y != y || Z != z) e.setCancelled(true);
		}
	}

	@Override
	@EventHandler
	public void onQuitEvent(PlayerQuitEvent e) {
		if(running){
			pm.restoreInv(p);
			p.removePotionEffect(PotionEffectType.INVISIBILITY);
		}
	}

	@Override
	public Player getPlayer() {
		return p;
	}

	@Override
	public EmotionType getType() {
		return EmotionType.WOAW;
	}

	
	
}
