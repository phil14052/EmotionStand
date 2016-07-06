package me.phil14052.EmotionStand;

import me.phil14052.EmotionStand.GUIs.ChooseEmotionGUI;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MainCommand implements CommandExecutor{

	
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(!cmd.getName().equalsIgnoreCase("emotions")) return true;
		if(args.length < 1){
			if(!(sender instanceof Player)) return false;
			Player p = (Player) sender;
			new ChooseEmotionGUI(p).open();
			return true;
		}
		if(args[0].equalsIgnoreCase("reload")){
			//Do something
			return true;
		}else{
			//Unknown argument
			// DO SOMETHING
			return false;
		}
		   
	}
	
}
