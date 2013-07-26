package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

import org.lwjgl.opengl.GL11;


/**
 * 
 * Simple view which only displays text
 *
 */
public class GuiViewText extends GuiView {
	
	private String text;
	
	public GuiViewText(int x, int y, int w, int h, String text) {
		super(x, y, w, h);
		this.text = text;
	}

	@Override
	public void drawBackground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
	}
	
	/**
	 * The minecraft font renderer seems to have no method to get the string baseline so "guestimate" it here
	 */
	private static final int STRING_DESCENT = 1;
	
	/**
	 * Draws a simple string centered in the view
	 */
	@Override
	public void drawForeground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		GL11.glColor4f(1, 1, 1, 1);
		int posX = (w - gui.getFontRenderer().getStringWidth(text)) / 2 + convertXtoScreen(0);
		int posY = (h - gui.getFontRenderer().FONT_HEIGHT) / 2 + convertYtoScreen(0) + STRING_DESCENT;
		gui.getFontRenderer().drawString(text, posX, posY, 0xFFFFFF);
	}
	
}
