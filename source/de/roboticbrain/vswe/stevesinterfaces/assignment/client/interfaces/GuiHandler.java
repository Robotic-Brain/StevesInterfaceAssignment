package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.IGuiHandler;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;

public class GuiHandler implements IGuiHandler {
	
	@Override
	public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GuiInfo.WOOD_GENERATOR_GUI:
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te != null && te instanceof TileEntitieWoodGenerator) {
					return new ContainerWoodGenerator(player.inventory, (TileEntitieWoodGenerator)te);
				}
				break;
		}
		
		return null;
	}
	
	@Override
	public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
		switch (id) {
			case GuiInfo.WOOD_GENERATOR_GUI:
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if (te != null && te instanceof TileEntitieWoodGenerator) {
					return new GuiWoodGenerator(player.inventory, (TileEntitieWoodGenerator)te);
				}
				break;
		}
		
		return null;
	}
	
}
