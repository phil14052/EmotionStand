package me.phil14052.EmotionStand.Emotions;

import me.phil14052.EmotionStand.EmotionStand;
import me.phil14052.EmotionStand.PlayerManager;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

public class SitEmotion implements Emotion,Listener{

	private ArmorStand as;
	private BukkitTask bt;
	private EmotionStand plugin;
	private Player p;
	private boolean canMove = true;
	private boolean running = false;
	private PlayerManager pm;
	public SitEmotion(Player p){
		plugin = EmotionStand.getInstance();
		Bukkit.getPluginManager().registerEvents(this, plugin);
		this.p = p;
		this.canMove = false;
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
		EulerAngle pose = as.getRightLegPose();
		pose = new EulerAngle(0F, 0F, 0F);
		as.setRightLegPose(pose);
		EulerAngle pose2 = as.getLeftLegPose();
		pose = new EulerAngle(0F, 0F, 0F);
		as.setLeftLegPose(pose2);
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
				
				if(!p.isOnline()){
					stop();
				}
				if(as.isDead()){
					stop();
					return;
				}
				as.setFireTicks(0);
				as.setFallDistance(0F);
				as.setMaxHealth(50D);
				as.setHealth(50D);
				double pitch = p.getLocation().getPitch();
				double e = 0.007550782857; //1% of the rotation (2,71828182845904523536028747135266249/360)
				as.setHeadPose(new EulerAngle((e*pitch)*2,0D, 0D));
				as.setCustomName("Sitting...");
				p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 255, 5, false, false));
				
				if(frame > 20 && frame < 30){
					EulerAngle pose = as.getRightLegPose();
					pose = pose.subtract(0.18F, 0F, 0F);
					as.setRightLegPose(pose);
					EulerAngle pose2 = as.getLeftLegPose();
					pose2 = pose2.subtract(0.18F, 0F, 0F);
					as.setLeftLegPose(pose2);
					Location loc = p.getLocation();
					loc.setY(loc.getY()-0.35D);
					loc.setYaw(p.getLocation().getYaw());
					loc.setPitch(p.getLocation().getPitch());
					as.teleport(loc);
				}else if(frame < 20){
					as.teleport(p);
				}else{
					Location loc = p.getLocation();
					loc.setY(loc.getY()-0.35D);
					loc.setYaw(p.getLocation().getYaw());
					loc.setPitch(p.getLocation().getPitch());
					as.teleport(loc);
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
			if(X != x || Y != y || Z != z) {
				stop();
				e.setCancelled(true);
			}
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
		return EmotionType.SIT;
	}
	
}
