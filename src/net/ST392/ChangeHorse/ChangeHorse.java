//author: ST392
//version 1.0

package net.ST392.ChangeHorse;

import org.bukkit.plugin.java.JavaPlugin;

public class ChangeHorse extends JavaPlugin{

	private CHCommandExecutor commandExecutor;
	protected CHLogHandler log; public CHLogHandler getLog() { return log; }
	
	@Override
	public void onEnable(){
		//Log
		this.log = new CHLogHandler(this);
		
		//Command Handler
		this.commandExecutor = new CHCommandExecutor(this);
		this.getCommand("changehorse").setExecutor(this.commandExecutor);
		this.getCommand("ch").setExecutor(this.commandExecutor);
	}
	
	@Override
	public void onDisable(){
		
	}
	
}
