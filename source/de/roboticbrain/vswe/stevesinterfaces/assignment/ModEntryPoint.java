package de.roboticbrain.vswe.stevesinterfaces.assignment;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import cpw.mods.fml.common.network.NetworkRegistry;
import de.roboticbrain.vswe.stevesinterfaces.assignment.blocks.Blocks;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.GuiHandler;
import de.roboticbrain.vswe.stevesinterfaces.assignment.config.ConfigHandler;
import de.roboticbrain.vswe.stevesinterfaces.assignment.network.PacketHandler;
import de.roboticbrain.vswe.stevesinterfaces.assignment.proxies.CommonProxy;

@Mod(	modid = ModInfo.ID,
		 name = ModInfo.NAME,
	  version = ModInfo.VERSION)
@NetworkMod(	channels = {ModInfo.CHANNEL},
	  clientSideRequired = true,
	  serverSideRequired = false,
	  	   packetHandler = PacketHandler.class)
public class ModEntryPoint {
	
	@Instance(ModInfo.ID)
	public static ModEntryPoint instance;
	
	@SidedProxy(clientSide = "de.roboticbrain.vswe.stevesinterfaces.assignment.proxies.ClientProxy",
				serverSide = "de.roboticbrain.vswe.stevesinterfaces.assignment.proxies.CommonProxy")
	public static CommonProxy proxy;
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ConfigHandler.init(event.getSuggestedConfigurationFile());
		Blocks.init();
		
		proxy.initSounds();
		proxy.initRenderers();
	}
	
	@EventHandler
	public void init(FMLInitializationEvent event) {
		Blocks.addNames();
		
		Blocks.registerTileEntities();
		
		NetworkRegistry.instance().registerGuiHandler(instance, new GuiHandler());
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		
	}
}
