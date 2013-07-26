package de.roboticbrain.vswe.stevesinterfaces.assignment.network;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;

import com.google.common.io.ByteArrayDataInput;
import com.google.common.io.ByteStreams;

import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import de.roboticbrain.vswe.stevesinterfaces.assignment.ModInfo;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.ContainerWoodGenerator;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;

public class PacketHandler implements IPacketHandler {
	
	/**
	 * Id of wood packet
	 */
	private static final int WOOD_PACKET_ID = 0;
	
	@Override
	public void onPacketData(INetworkManager manager, Packet250CustomPayload packet, Player player) {
		ByteArrayDataInput reader = ByteStreams.newDataInput(packet.data);
		
		EntityPlayer entityPlayer = (EntityPlayer)player;
		
		byte packetId = reader.readByte();
		
		switch (packetId) {
			case WOOD_PACKET_ID:
				byte type = reader.readByte();
				
				Container container = entityPlayer.openContainer;
				if (container != null && container instanceof ContainerWoodGenerator) {
					TileEntitieWoodGenerator generator = ((ContainerWoodGenerator)container).getTileEntity();
					generator.setWoodType(type);
				}
				break;
		}
	}
	
	/**
	 * Send selected wood type to server
	 * @param type
	 */
	public static void sendWoodPacket(byte type) {
		ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
		DataOutputStream dataStream = new DataOutputStream(byteStream);

		try {
			dataStream.writeByte((byte)WOOD_PACKET_ID);
			dataStream.writeByte(type);			
			
			PacketDispatcher.sendPacketToServer(PacketDispatcher.getPacket(ModInfo.CHANNEL, byteStream.toByteArray()));
		} catch(IOException ex) {
			System.err.append("Failed to send button click packet");
		}
	}
	
}
