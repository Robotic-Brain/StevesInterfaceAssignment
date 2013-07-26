package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

import java.util.List;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.inventory.Container;


/**
 * 
 * Abstract base class to publish all fields and methods of GuiContainer
 * 		need by external Gui classes
 *
 */
public abstract class GuiContainerViewContainer extends GuiContainer {
	
	public GuiContainerViewContainer(Container container) {
		super(container);
	}
	
	/**
	 * @return Standard font renderer
	 */
	public FontRenderer getFontRenderer() {
		return fontRenderer;
	}
	
	/**
	 * @param List of text to draw
	 * @param X coordinate
	 * @param Y coordinate
	 * @param font renderer
	 */
	@Override
	public void drawHoveringText(List list, int x, int y, FontRenderer font) {
		super.drawHoveringText(list, x, y, font);
	}
	
}
