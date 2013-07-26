package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

import java.util.ArrayList;

import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.GuiColor;

/**
 * 
 * View for horizontal progress bars
 * set a custom tooltip!
 *
 */
public class GuiViewProgress extends GuiView {
	
	public GuiViewProgress(int x, int y, int w, int h) {
		super(x, y, w, h);
		
		showValue = true;
	}
	
	private DataSourceInt source;
	private String hoverString;
	
	/**
	 * Show value in tooltip
	 */
	private boolean showValue;
	
	private int u;
	private int v;
	
	@Override
	public void drawBackground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		double totalSize = (double)source.getMaxValue() - (double)source.getMinValue();
		double progress = (double)source.getValue() / totalSize * (double)w;
		
		int start = convertXtoScreen(0);
		int stop = (int)progress;
		
		if (progress < 0) {
			progress *= -1.0;
			stop = w - (int)progress;
		}
		
		gui.drawTexturedModalRect(start, convertYtoScreen(0), u, v, stop, h);
	}
	
	@Override
	public void drawForeground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		if (inRect(mouseX, mouseY)) {
			ArrayList<String> text = new ArrayList<String>();
			if (hoverString != null) {
				text.add(hoverString);
			}
			if (showValue) {
				String line = "" + GuiColor.LIGHTGRAY + source.getValue();
				if (source.getMaxValue() > source.getMinValue()) {
					line += " / " + source.getMaxValue();
				}
				text.add(line);
			}
			if (!text.isEmpty()) {
				gui.drawHoveringText(text, mouseX, mouseY, gui.getFontRenderer());
			}
		}
	}
	
	/**
	 * Set the data source for this progress bar
	 * @param source
	 */
	public void setDataSource(DataSourceInt s) {
		source = s;
	}
	
	/**
	 * Set String for tooltip
	 * @param string	Text in tooltip
	 */
	public void setTooltip(String text) {
		hoverString = text;
	}
	
	/**
	 * Set source coordinates of texture
	 * @param x		offset in texture
	 * @param y		offset in texture
	 */
	public void setTextureSource(int x, int y) {
		this.u = x;
		this.v = y;
	}
	
	/**
	 * Flag to show/hide value in tooltip
	 * @param flag
	 */
	public void setValueVisible(boolean flag) {
		showValue = flag;
	}
	
}
