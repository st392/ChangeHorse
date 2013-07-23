package net.ST392.ChangeHorse;

import java.util.logging.Logger;
import org.bukkit.plugin.PluginDescriptionFile;

public class CHLogHandler {

	//=====================================================
		private ChangeHorse plugin;
		private Logger logger;
		public CHLogHandler(ChangeHorse plugin){
			this.plugin = plugin;
			this.logger = Logger.getLogger("Minecraft");
		}
		//=====================================================
		
		//Generate message for console
		private String buildString(String message) {
			PluginDescriptionFile pdFile = plugin.getDescription();
			return "[" + pdFile.getName() +"] (" + pdFile.getVersion() + ") " + message;
		}
		
		//Log message as INFO
		public void info(String message){
			this.logger.info(this.buildString(message));
		}
		
		//Log message as WARNING
		public void warn(String message){
			this.logger.warning(this.buildString(message));
		}

}
