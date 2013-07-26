package de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.views;

import java.util.ArrayList;

import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.DataSourceWoodTypes;
import de.roboticbrain.vswe.stevesinterfaces.assignment.client.interfaces.GuiColor;

public class GuiViewButton extends GuiView {
	
	/**
	 * IMPORTANT: These are simple buttons so the textures for the different states have to lie above each other!
	 * 				Normal State = (x, y)
	 * 				Hover State  = (x, y +   h)
	 * 				Active State = (x, y + 2*h)
	 * 
	 * @param u		Texture x coordinate
	 * @param v		Texture y coordinate
	 */
	public GuiViewButton(int x, int y, int w, int h, int u, int v) {
		super(x, y, w, h);
		
		this.u = u;
		this.v = v;
		
		setContentView(new GuiViewText(0, 0, w, h, GuiColor.WHITE + "Button"));
	}
	
	private int u;
	private int v;
	
	/**
	 * Interior view of button
	 * Note: (does receive no events!)
	 */
	private GuiView contentView;
	
	/**
	 * Tooltip of button
	 */
	private String tooltip;
	
	/**
	 * Current State
	 */
	private DataSourceBool source;
	
	/**
	 * State values
	 */
	public static final int NORMAL_STATE = 0;
	//public static final int HOVER_STATE = 1;
	public static final int ACTIVE_STATE = 2;
	
	@Override
	public void drawBackground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		contentView.drawBackground(gui, mouseX - x, mouseY - y);
		
		if (getState() == NORMAL_STATE && inRect(mouseX, mouseY)) {
			gui.drawTexturedModalRect(convertXtoScreen(0), convertYtoScreen(0), u, v + 1 * h, w, h);
		} else {
			gui.drawTexturedModalRect(convertXtoScreen(0), convertYtoScreen(0), u, v + getState() * h, w, h);
		}
	}
	
	@Override
	public void drawForeground(GuiContainerViewContainer gui, int mouseX, int mouseY) {
		contentView.drawForeground(gui, mouseX - x, mouseY - y);
		
		if (inRect(mouseX, mouseY)) {
			if (tooltip != null) {
				ArrayList<String> list = new ArrayList<String>();
				list.add(tooltip);
				
				// After this Method is called all text goes crazy?!
				gui.drawHoveringText(list, mouseX, mouseY, gui.getFontRenderer());
			}
		}
	}
	
	/**
	 * handler called on button action
	 */
	private IGuiCallback handler;
	
	/**
	 * aux data for handler
	 */
	private int handleData;
	
	public void setHandler(IGuiCallback hnd, int id) {
		handler = hnd;
		handleData = id;
	}
	
	@Override
	public void onMousePressed(int mouseX, int mouseY, int mouseButton) {
		if (handler != null) {
			handler.onViewClicked(this, handleData);
		}
	}
	
	/**
	 * Returns current button state
	 * @return
	 */
	public int getState() {
		return source.getValue() ? ACTIVE_STATE : NORMAL_STATE;
	}
	
	/**
	 * Sets the data source for this button
	 * @param dataSource	returning if this button is active
	 */
	public void setDataSource(DataSourceBool data) {
		source = data;
	}
	
	/**
	 * Sets the Buttons tooltip
	 * @param text
	 */
	public void setTooltip(String text) {
		tooltip = text;
	}
	
	/**
	 * Sets the view displayed in the button
	 * @param contentview
	 */
	public void setContentView(GuiView view) {
		contentView = view;
		view.setParentView(this);
	}
	
}
