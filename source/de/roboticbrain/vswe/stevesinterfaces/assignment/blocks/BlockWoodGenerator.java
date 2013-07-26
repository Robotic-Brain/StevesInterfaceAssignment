package de.roboticbrain.vswe.stevesinterfaces.assignment.blocks;

import cpw.mods.fml.common.network.FMLNetworkHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.roboticbrain.vswe.stevesinterfaces.assignment.ModEntryPoint;
import de.roboticbrain.vswe.stevesinterfaces.assignment.ModInfo;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.GuiInfo;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

public class BlockWoodGenerator extends BlockContainer {
	
	protected BlockWoodGenerator(int id) {
		super(id, Material.iron);
		setUnlocalizedName(BlockInfo.WOOD_GENERATOR_UNLOCALIZED_NAME);
		setCreativeTab(CreativeTabs.tabBlock);
		setHardness(2F);
		setStepSound(soundMetalFootstep);
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister register) {
		blockIcon = register.registerIcon(ModInfo.MAIN_ASSET_LOCATION + ":" + BlockInfo.WOOD_GENERATOR_TEXTURE);
	}
	
	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntitieWoodGenerator();
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, int id, int meta) {
		TileEntity te = world.getBlockTileEntity(x, y, z);
		
		if (te != null && te instanceof IInventory) {
			IInventory inv = (IInventory)te;
			
			for (int i = 0; i < inv.getSizeInventory(); i++) {
				ItemStack stack = inv.getStackInSlotOnClosing(i);
				
				if (stack != null) {
					// No randomization here since assignment focuses on interfaces
					// (also: standard randomization seems fine)
					EntityItem entity = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, stack);
					
					world.spawnEntityInWorld(entity);
				}
			}
		}
		
		super.breakBlock(world, x, y, z, id, meta);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!world.isRemote) {
			FMLNetworkHandler.openGui(player, ModEntryPoint.instance, GuiInfo.WOOD_GENERATOR_GUI, world, x, y, z);
		}
		return true;
	}
	
}
