package de.roboticbrain.vswe.stevesinterfaces.assignment.blocks;

import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;
import net.minecraft.block.Block;

public class Blocks {
	
	public static Block woodGenerator;
	
	public static void init() {
		woodGenerator = new BlockWoodGenerator(BlockInfo.WOOD_GENERATOR_ID);
		GameRegistry.registerBlock(woodGenerator, BlockInfo.WOOD_GENERATOR_KEY);
	}

	public static void addNames() {
		LanguageRegistry.addName(woodGenerator, BlockInfo.WOOD_GENERATOR_NAME);
	}

	public static void registerTileEntities() {
		GameRegistry.registerTileEntity(TileEntitieWoodGenerator.class, BlockInfo.WOOD_GENERATOR_TE_KEY);
	}
	
}
