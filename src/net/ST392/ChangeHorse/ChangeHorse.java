package net.ST392.ChangeHorse;

//import java.io.File;

import java.io.IOException;

import net.ST392.ChangeHorse.Updater.UpdateType;

//import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class ChangeHorse extends JavaPlugin{

	private CHCommandExecutor commandExecutor;
	protected CHLogHandler log; public CHLogHandler getLog() { return log; }
	
	//config settings
	protected Boolean checkUpdate;
	protected Boolean useMcstats;
	protected Boolean doUpdate;
	public String currentVersion;
	
	@Override
	public void onEnable(){
		//Log
		this.log = new CHLogHandler(this);
        currentVersion = getDescription().getVersion();
		
		// Make the data folder
        getDataFolder().mkdirs();
        // Create the default configuration file if one doesn't exist
        this.saveDefaultConfig();
        this.getConfig().addDefault("checkUpdate", true);
        this.getConfig().addDefault("doUpdate", true);
        this.getConfig().addDefault("enableMcstats", true);
        
        checkUpdate = this.getConfig().getBoolean("checkUpdate");
        doUpdate = this.getConfig().getBoolean("doUpdate");
        useMcstats = this.getConfig().getBoolean("enableMcstats");
        
        
        /**
         * Schedule a version check every 6 hours for update notification. First check 10 seconds after init. (gives
         * server chance to start
         */
        if (this.checkUpdate) {
            UpdateType upd = Updater.UpdateType.NO_DOWNLOAD;
            
            if (doUpdate) {
                upd = Updater.UpdateType.DEFAULT;
            }
            
            Updater updater = new Updater(this, "changehorse", this.getFile(), upd, true);
            Updater.UpdateResult result = updater.getResult();
            
            String name;
            long size;
            
            switch (result) {
                case SUCCESS:
                    // Success: The updater found an update, and has readied it to be loaded the next time the server restarts/reloads
                    break;
                case NO_UPDATE:
                    // No Update: The updater did not find an update, and nothing was downloaded.
                    name = updater.getLatestVersionString(); // Get the latest version
                    log.info(name + " is upto date.");
                    log.info("http://dev.bukkit.org/server-mods/changehorse/");
                    break;
                case FAIL_DOWNLOAD:
                    // Download Failed: The updater found an update, but was unable to download it.
                    name = updater.getLatestVersionString(); // Get the latest version
                    size = updater.getFileSize(); // Get latest size
                    log.info(name + " is available (" + size + " bytes) (You're using " + currentVersion + ")");
                    log.warn("Automatic Download Failed please visit");
                    log.info("http://dev.bukkit.org/server-mods/changehorse/");
                    break;
                case FAIL_DBO:
                case FAIL_NOVERSION:
                case FAIL_BADSLUG:
                    log.warn("Error during version check - " + result.toString());
                    break;
                case UPDATE_AVAILABLE:
                    name = updater.getLatestVersionString(); // Get the latest version
                    size = updater.getFileSize(); // Get latest size
                    log.warn(name + " is available (" + size + " bytes) (You're using " + currentVersion + ")");
                    log.info("http://dev.bukkit.org/server-mods/changehorse/");
                    break;
            }
        }
        
        //mcstats
        if(useMcstats){
        	try {
        	    Metrics metrics = new Metrics(this);
        	    metrics.start();
        	    log.info("Using McStats");
        	} catch (IOException e) {
        	    log.info("McStats failed to start");
        	}
        }
        
		//Command Handler
		this.commandExecutor = new CHCommandExecutor(this);
		this.getCommand("changehorse").setExecutor(this.commandExecutor);
		this.getCommand("chs").setExecutor(this.commandExecutor);
	}

	/*private void updateConfiguration() {
		final YamlConfiguration yml = new ChangHorseConfig().loadConfig();
		this.checkUpdate = yml.getBoolean("checkUpdate", true);
		this.doUpdate = yml.getBoolean("doUpdate", true);
	}*/

	@Override
	public void onDisable(){
		this.commandExecutor = null;
		this.log = null;
	}
	
}
