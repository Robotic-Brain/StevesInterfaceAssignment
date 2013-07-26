package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import de.roboticbrain.vswe.stevesinterfaces.assignment.ModInfo;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.GuiContainerViewContainer;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.GuiView;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.GuiViewButton;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.GuiViewProgress;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.GuiViewText;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.GuiViewWrapper;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views.IGuiCallback;
import de.roboticbrain.vswe.stevesinterfaces.assignment.network.PacketHandler;
import de.roboticbrain.vswe.stevesinterfaces.assignment.tileentities.TileEntitieWoodGenerator;

@SideOnly(Side.CLIENT)
public class GuiWoodGenerator extends GuiContainerViewContainer implements IGuiCallback {
	
	private TileEntitieWoodGenerator generator;
	
	public GuiWoodGenerator(InventoryPlayer inv, TileEntitieWoodGenerator te) {
		super(new ContainerWoodGenerator(inv, te));
		
		generator = te;
		
		xSize = 176;
		ySize = 218;
		
	}
	
	private static final ResourceLocation texture = new ResourceLocation(ModInfo.MAIN_ASSET_LOCATION, "textures/gui/woodgenerator.png");
	
	private GuiViewWrapper mainView;
	
	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		
		// Draw main texture
		Minecraft.getMinecraft().func_110434_K().func_110577_a(texture);
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, ySize);
		
		// Draw main view
		mainView.drawBackground(this, mouseX, mouseY);
		
		// Manually display Preview Item since it needs different textures
		Minecraft.getMinecraft().func_110434_K().func_110577_a(TextureMap.field_110575_b);
		drawTexturedModelRectFromIcon(guiLeft + 114 + 19, guiTop + 74 + 19 - 3, Block.sapling.getIcon(0, generator.getWoodType()), 16, 16);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString("Wood Generator", 8, 6, 0x404040);
		
		// Draw main view
		mainView.drawForeground(this, mouseX, mouseY);
		mainView.drawHoverBoxes(this, mouseX, mouseY);
	}
	
	@Override
	protected void mouseClicked(int x, int y, int button) {
		super.mouseClicked(x, y, button);
		mainView.onMousePressed(x, y, button);
	}
	
	@Override
	protected void mouseClickMove(int x, int y, int button, long timeSinceClicked) {
		super.mouseClickMove(x, y, button, timeSinceClicked);
		mainView.onMouseDragged(x, y, button, timeSinceClicked);
	}
	
	@Override
	protected void mouseMovedOrUp(int x, int y, int button) {
		super.mouseMovedOrUp(x, y, button);
		mainView.onMouseReleased(x, y, button);
	}
	
	/**
	 * I couldn't figure out how to get the item/block name from minecraft
	 * so define the Strings here
	 */
	private static final ArrayList<String> woodNames;
	
	static {
		woodNames = new ArrayList<String>();
		woodNames.add("Oak");
		woodNames.add("Spruce");
		woodNames.add("Birch");
		woodNames.add("Jungle");
	}
	
	@Override
	public void initGui() {
		super.initGui();
		mainView = new GuiViewWrapper(guiLeft, guiTop, xSize, ySize);
		//mainView = new GuiViewDummy(0, 0, 100, 100);
		
		// Add ProgressBar
		GuiViewProgress progress = new GuiViewProgress(53, 34, 51, 18);
		progress.setDataSource(new DataSourceTime(generator));
		progress.setTextureSource(176, 0);
		progress.setTooltip("Progress");
		
		mainView.addView(progress);
		
		
		// Add Wood Type Buttons
		GuiViewButton button;
		for (int x = 0; x < 2; x++) {
			for (int y = 0; y < 2; y++) {
				button = new GuiViewButton(8 + x*49, 83 + y*19, 44, 14, 176, 18);
				button.setHandler(this, y + x*2);
				button.setDataSource(new DataSourceWoodTypes(generator, y + x*2));
				button.setTooltip(woodNames.get(y + x*2) + " Wood");
				button.setContentView(new GuiViewText(0, 0, 44, 14, woodNames.get(y + x*2)));
				mainView.addView(button);
			}
		}
	}
	
	
	@Override
	public void onViewClicked(GuiView sender, int aux) {
		if (sender instanceof GuiViewButton) {
			GuiViewButton button = (GuiViewButton)sender;
			generator.setWoodType(aux);
			PacketHandler.sendWoodPacket((byte)aux);
		}
	}
	
}
