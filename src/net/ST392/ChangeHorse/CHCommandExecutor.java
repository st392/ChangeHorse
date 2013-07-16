package net.ST392.ChangeHorse;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Horse;
import org.bukkit.entity.Player;

public class CHCommandExecutor implements CommandExecutor {

	//Class that handles slash commands from user
	//=============================================
	private ChangeHorse plugin;
	public CHCommandExecutor(ChangeHorse plugin) {
		this.plugin = plugin;
	}
	//=============================================
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		//Make sure command was not sent from console
		if (sender instanceof Player == false){
			plugin.getLog().info("/ChangeHorse is only available in game.");
			return true;
		}
		
		//Convert sender to Player
		Player player = (Player) sender;
		if(!(player.hasPermission("ChangeHorse.change"))){
			player.sendMessage(ChatColor.RED + "You don't have permission!");
			return true;
		}
		
		if (!player.isInsideVehicle() || !(player.getVehicle().getType().getName().equals("EntityHorse"))) {
			
			this.plugin.getLog().sendPlayerNormal(player, "Must have argument and must be riding a horse.");
			
			return false; 
		}

		if(args.length == 0){
			this.plugin.getLog().sendPlayerNormal(player, "/ChangeHorse (property)");
			this.plugin.getLog().sendPlayerNormal(player, "Properties are: Type, Color, Style");		
			return true;
		}		
		
		
		
		//Get the Horse
		Horse horse = (Horse) player.getVehicle();
		
		String property = null;
		try { property = args[0]; } catch(NumberFormatException e) {}
		
		if(property == null){
			player.sendMessage("Invalid argument");
			return true;
		}
			
		if(args.length > 2){
			args[1] = args[1] + args[2];
		}
		
		if(property.toLowerCase().equals("type") || property.toLowerCase().equals("t")){
			if(args.length == 1){
				this.plugin.getLog().sendPlayerNormal(player, "Type options are:");
				for(Horse.Variant variant: Horse.Variant.values()){
					this.plugin.getLog().sendPlayerNormal(player, "     " + variant.toString().toLowerCase());
				}
			}else{
				for(Horse.Variant variant: Horse.Variant.values()){
					if(variant.toString().toLowerCase().replaceAll("_", "").replaceAll("horse", "").equals(args[1].toLowerCase().replaceAll("_", "").replaceAll("horse", ""))){
						if(variant != Horse.Variant.HORSE && horse.getInventory().getArmor() != null){
							player.sendMessage(ChatColor.RED + "Please remove horse armor before changing horse type.");
							return true;
						}
						horse.setVariant(variant);
						this.plugin.getLog().sendPlayerNormal(player, "Horse type set to " + variant.toString());
						return true;
					}
				}
				this.plugin.getLog().sendPlayerNormal(player, "Invalid arguement.  For list of Types: /ChangeHorse Type");
				return true;
			}
		}else if(property.toLowerCase().equals("color") || property.toLowerCase().equals("c")){
			if(args.length == 1){
				this.plugin.getLog().sendPlayerNormal(player, "Color options are:");
				for(Horse.Color color: Horse.Color.values()){
					this.plugin.getLog().sendPlayerNormal(player, "     " + color.toString().toLowerCase().replaceAll("_", ""));
				}
			}else{
				if(horse.getVariant() == Horse.Variant.HORSE){
					for(Horse.Color color: Horse.Color.values()){
						if(color.toString().toLowerCase().replaceAll("_", "").equals(args[1].toLowerCase())){
							horse.setColor(color);
							this.plugin.getLog().sendPlayerNormal(player, "Horse color set to " + color.toString());
							return true;
						}
					}
					this.plugin.getLog().sendPlayerNormal(player, "Invalid arguement.  For list of Colors: /ChangeHorse Color");
					return true;
				}else{
					player.sendMessage(ChatColor.RED + "Horse colors only apply to normal horses");
				}
			}
		}else if(property.toLowerCase().equals("style") || property.toLowerCase().equals("s")){
			if(args.length == 1){
				this.plugin.getLog().sendPlayerNormal(player, "Style options are:");
				for(Horse.Style style: Horse.Style.values()){
					this.plugin.getLog().sendPlayerNormal(player, "     " + style.toString().toLowerCase());
				}
			}else{
				if(horse.getVariant() == Horse.Variant.HORSE){
					for(Horse.Style style: Horse.Style.values()){
						if(style.toString().toLowerCase().replaceAll("_", "").equals(args[1].toLowerCase().replaceAll("_", ""))){
							horse.setStyle(style);
							this.plugin.getLog().sendPlayerNormal(player, "Horse style set to " + style.toString());
							return true;
						}
					}
					this.plugin.getLog().sendPlayerNormal(player, "Invalid arguement.  For list of Styles: /ChangeHorse Style");
				}else{
					player.sendMessage(ChatColor.RED + "Horse styles only apply to normal horses");
				}
			}
		}else{
			this.plugin.getLog().sendPlayerNormal(player, "Invalid arguments.  /changehorse (property) (value)");
			return true;
		}
		
		return true;
	}
}
