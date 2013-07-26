package de.roboticbrain.vswe.stevesinterfaces.assignment.config;

import java.io.File;

import de.roboticbrain.vswe.stevesinterfaces.assignment.blocks.BlockInfo;
import net.minecraftforge.common.Configuration;

public class ConfigHandler {

	public static void init(File file) {
		Configuration config = new Configuration(file);
		
		config.load();
		
		BlockInfo.WOOD_GENERATOR_ID = config.getBlock(BlockInfo.WOOD_GENERATOR_KEY, BlockInfo.WOOD_GENERATOR_DEFAULT).getInt();
		
		if (config.hasChanged()) {
			config.save();
		}
	}
	
}
